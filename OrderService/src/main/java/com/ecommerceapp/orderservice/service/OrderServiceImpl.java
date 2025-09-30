package com.ecommerceapp.orderservice.service;

import com.ecommerceapp.orderservice.cart_client.CartItem;
import com.ecommerceapp.orderservice.cart_client.ShoppingCartClient;
import com.ecommerceapp.orderservice.cart_client.ShoppingCartResponse;
import com.ecommerceapp.orderservice.exception.BadRequestException;
import com.ecommerceapp.orderservice.exception.ResourceNotFoundException;
import com.ecommerceapp.orderservice.kafka.OrderProducer;
import com.ecommerceapp.orderservice.mapper.OrderMapper;
import com.ecommerceapp.orderservice.order.*;
import com.ecommerceapp.orderservice.payment.InternalStkPushRequest;
import com.ecommerceapp.orderservice.product_client.Product;
import com.ecommerceapp.orderservice.product_client.ProductClient;
import com.ecommerceapp.orderservice.product_client.ProductRequest;
import com.ecommerceapp.orderservice.product_client.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final ShoppingCartClient cartClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;
    private final OrderStatusPublisher orderStatusPublisher;

    @Override
    public Order makeOrder(Long userId, OrderRequestDto orderRequest) {

        ApiResponse<ShoppingCartResponse> cart = cartClient.getCart(userId).orElseThrow(() ->
                new ResourceNotFoundException("Cart not found")
        );

        if (cart.getData().cartItems().isEmpty()) throw new BadRequestException("Cart cannot be empty");

        Order order = orderRepository.save(mapper.fromCart(cart.getData(), orderRequest));

        InternalStkPushRequest request = new InternalStkPushRequest();
        int amount = cart.getData().totalPrice().intValue();
        request.setAmount(BigDecimal.valueOf(amount));
        request.setPhoneNumber(orderRequest.phoneNumber());
        request.setOrderId(order.getId());

        orderStatusPublisher.publisherOrderEvent(request, OrderStatus.CREATED, orderRequest);

        orderProducer.sendOrderConfirmation(new OrderConfirmation(
                order.getId(),
                order.getOrderStatus(),
                order.getUserEmail(),
                BigDecimal.valueOf(amount),
                order.getCartItems()
        ));

        updateProductInventory(cart.getData());
        cartClient.clearCart(userId);

        return order;
    }

    @Override
    public Order updateOrderStatus(String orderId, String status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new ResourceNotFoundException("Order not found")
        );

        order.setOrderStatus(OrderStatus.valueOf(status));

        orderRepository.save(order);
        // update and send email

        return order;
    }

    @Override
    public List<Order> getAllOrders() {
        try {
            return orderRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ApiResponse<List<Order>> getUserOrderBasedOnStatus(Long userId, String status) {
        List<Order> orders = orderRepository.findByUserIdAndOrderStatus(userId, status);

        return new ApiResponse<>(
                HttpStatus.OK.value(),
                status + " user orders retrieved",
                orders
        );
    }

    @Override
    public ApiResponse<List<Order>> getOrdersBasedOnStatus(String status) {

        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Define the status");
        }
        List<Order> orders = orderRepository.findByOrderStatus(status);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                status + " orders retrieved successfully",
                orders
        );
    }

    @Override
    public Order findOrderById(String orderId) {
        return orderRepository.findById(orderId).orElseThrow(() ->
                new ResourceNotFoundException("Order with id " + orderId + " not found")
        );
    }

    @Override
    public ApiResponse<String> cancelOrder(String orderId) {
        log.info("==============>Method is called");
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new ResourceNotFoundException("Order of the id " + orderId + " not found")
        );

        List<CartItem> cartItems = order.getCartItems();

        log.info("===================>cart items: {}", cartItems);
        ExecutorService executorService = Executors.newFixedThreadPool(Math.min(cartItems.size(), 10));

        List<CompletableFuture<Void>> futures = cartItems.stream()
                .map(cartItem -> CompletableFuture.runAsync(() -> {
                    log.info("------------->Cart item: {}", cartItem);
                    Product product = productClient.getProductById(cartItem.productId()).getProduct();

                    ProductRequest productRequest = new ProductRequest(
                            product.getProductId(),
                            product.getProductName(),
                            product.getProductDescription(),
                            cartItem.quantity() + product.getAvailableQuantity(),
                            product.getItemImageUrl(),
                            product.getItemPrice(),
                            product.getProductCategory()
                    );
                    productClient.updateProduct(productRequest);

                    log.info("================>add to cart: {}", order.getUserId());
                    log.info("---------------->Cart item: {}", cartItem);

                    cartClient.addToCart(cartItem, order.getUserId());

                }, executorService)).toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
//        delete order from the order db.
        orderRepository.delete(order);

        executorService.shutdown();
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Order successfully cancelled",
                null
        );
    }

    private void updateProductInventory(ShoppingCartResponse cartResponse) {
        List<CompletableFuture<Void>> updateFutures = cartResponse.cartItems().stream()
                .map(cartItem -> CompletableFuture.runAsync(() -> updateSingleProductInventory(cartItem)))
                .toList();
        try {
            CompletableFuture.allOf(updateFutures.toArray(new CompletableFuture[0])).join();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void updateSingleProductInventory(CartItem cartItem) {
        try {
            ProductResponse productResponse = productClient.getProductById(cartItem.productId());
            ProductRequest request = getProductRequest(cartItem, productResponse);

            productClient.updateProduct(request);
        } catch (Exception error) {
            log.error(error.getMessage());
            throw new RuntimeException(error.getMessage());
        }
    }

    private static ProductRequest getProductRequest(CartItem cartItem, ProductResponse productResponse) {
        Product product = productResponse.getProduct();

        int newAvailability = product.getAvailableQuantity() - cartItem.quantity();

        return new ProductRequest(
                product.getProductId(),
                product.getProductName(),
                product.getProductDescription(),
                newAvailability,
                product.getItemImageUrl(),
                product.getItemPrice(),
                product.getProductCategory()
        );
    }
}
