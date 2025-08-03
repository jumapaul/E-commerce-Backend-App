package com.ecommerceapp.orderservice.order;

import com.ecommerceapp.orderservice.cart_client.CartItem;
import com.ecommerceapp.orderservice.cart_client.ShoppingCartClient;
import com.ecommerceapp.orderservice.cart_client.ShoppingCartResponse;
import com.ecommerceapp.orderservice.exception.ResourceNotFoundException;
import com.ecommerceapp.orderservice.mapper.OrderMapper;
import com.ecommerceapp.orderservice.product_client.Product;
import com.ecommerceapp.orderservice.product_client.ProductClient;
import com.ecommerceapp.orderservice.product_client.ProductRequest;
import com.ecommerceapp.orderservice.product_client.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final ShoppingCartClient cartClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderRepository orderRepository;

    @Override
    public Order makeOrder(Long userId, String authHeader) {

        ApiResponse<ShoppingCartResponse> cart = cartClient.getCart(userId, authHeader).orElseThrow(() ->
                new ResourceNotFoundException("Cart not found")
        );

        // make payment - ignore for now

        Order order = mapper.fromCart(cart.getData());
        orderRepository.save(order);

        updateProductInventory(cart.getData(), authHeader);

        deleteCart(userId, authHeader);
        //Send notification - ignore for now

        return order;
    }

    @Override
    public Order updateOrderStatus(String orderId, String status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new ResourceNotFoundException("Order not found")
        );

        order.setStatus(Status.valueOf(status));

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
        List<Order> orders = orderRepository.findByUserIdAndStatus(userId, status);

        log.info("-------------->: {}", orders);
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
        List<Order> orders = orderRepository.findByStatus(status);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                status + " orders retrieved successfully",
                orders
        );
    }

    private void updateProductInventory(ShoppingCartResponse cartResponse, String authHeader) {
        List<CompletableFuture<Void>> updateFutures = cartResponse.cartItems().stream()
                .map(cartItem -> CompletableFuture.runAsync(() -> updateSingleProductInventory(cartItem, authHeader)))
                .toList();
        try {
            CompletableFuture.allOf(updateFutures.toArray(new CompletableFuture[0])).join();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void updateSingleProductInventory(CartItem cartItem, String authHeader) {
        try {
            ProductResponse productResponse = productClient.getProductById(cartItem.productId(), authHeader);
            ProductRequest request = getProductRequest(cartItem, productResponse);

            productClient.updateProduct(request, authHeader);
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

    private void deleteCart(Long userId, String authHeader) {
        try {
            cartClient.deleteCart(userId, authHeader);
        } catch (Exception e) {

            throw new RuntimeException(e);
        }

    }
}
