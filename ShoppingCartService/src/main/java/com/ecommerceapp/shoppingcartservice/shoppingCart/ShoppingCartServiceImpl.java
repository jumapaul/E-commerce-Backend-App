package com.ecommerceapp.shoppingcartservice.shoppingCart;

import com.ecommerceapp.shoppingcartservice.shoppingCart.cart.*;
import com.ecommerceapp.shoppingcartservice.shoppingCart.exception.ResourceNotFoundException;
import com.ecommerceapp.shoppingcartservice.shoppingCart.productClient.ProductClient;
import com.ecommerceapp.shoppingcartservice.shoppingCart.productClient.ProductResponse;
import com.ecommerceapp.shoppingcartservice.shoppingCart.user_client.UserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ProductClient productClient;
    private final UserClient userClient;
    private final ShoppingCartRepository repository;
    private final CartMapper mapper;

    @Override
    public ApiResponse<Cart> addProductToCart(CartItemRequest request, Long userId) {

        Cart cart = repository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("Cart not found")
        );

        Optional<CartItem> cartItem = cart.getCartItems().stream().filter(item ->
                item.getProductId().equals(request.productId())).findFirst();

        ProductResponse product = productClient.getProductById(request.productId()).orElseThrow(() ->
                new ResourceNotFoundException("Item with productId " + request.productId() + "not found")
        );

        if (cartItem.isPresent()) {
            CartItem item = cartItem.get();
            int newQuantity = item.getQuantity() + request.quantity();


            if (newQuantity > product.data().availableQuantity())
                throw new IllegalArgumentException("Item out of stock");

            item.setQuantity(newQuantity);
            BigDecimal newTotalPrice = item.getUnitPrice().multiply(BigDecimal.valueOf(newQuantity));
            item.setTotalPrice(newTotalPrice);

        } else {
            if (request.quantity() > product.data().availableQuantity())
                throw new IllegalArgumentException("Item out of stock");


            cart.getCartItems().add(mapper.fromProductResponse(product.data(), request.quantity()));
        }

        BigDecimal totalPrice = cart.getCartItems().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalPrice(totalPrice);
        repository.save(cart);

        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Item added to cart successfully",
                cart
        );
    }

    @Override
    public ApiResponse<Cart> getCart(Long userId) {
        Cart cart = repository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("Cart not found")
        );

        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Items retrieved successfully",
                cart
        );
    }

    @Override
    public ApiResponse<Cart> removeFromCart(Long userId, String itemId) {
        Cart cart = repository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("Cart not found")
        );

        cart.getCartItems().removeIf(item -> item.getProductId().equals(itemId));

        BigDecimal newTotal = cart.getCartItems().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalPrice(newTotal);

        repository.save(cart);

        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Item removed successfully",
                cart
        );
    }

    @Override
    public ApiResponse<String> clearCart(Long userId) {
        Cart cart = repository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("Cart not found")
        );

        cart.getCartItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
        repository.save(cart);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Cart successfully cleared",
                null
        );
    }
}
