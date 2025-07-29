package com.ecommerceapp.shoppingcartservice.shoppingCart;

import com.ecommerceapp.shoppingcartservice.shoppingCart.cart.*;
import com.ecommerceapp.shoppingcartservice.shoppingCart.exception.ResourceNotFoundException;
import com.ecommerceapp.shoppingcartservice.shoppingCart.productClient.ProductClient;
import com.ecommerceapp.shoppingcartservice.shoppingCart.productClient.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ProductClient productClient;
    private final ShoppingCartRepository repository;
    private final CartMapper mapper;

    @Override
    public ApiResponse<Cart> addProductToCart(CartItem request, Long userId) {

        Cart cart = repository.findById(userId).orElseGet(() ->
                createNewCart(userId)
        );

        Optional<CartItem> cartItem = cart.getCartItems().stream().filter(item ->
                item.getProductId().equals(request.getProductId())).findFirst();

        ProductResponse product = productClient.getProductById(request.getProductId()).orElseThrow(() ->
                new ResourceNotFoundException("Item with id " + request.getProductId() + "not found")
        );

        if (cartItem.isPresent()) {
            CartItem item = cartItem.get();
            int newQuantity = item.getQuantity() + request.getQuantity();


            if (newQuantity > product.data().availableQuantity())
                throw new IllegalArgumentException("Item out of stock");

            item.setQuantity(newQuantity);
            BigDecimal newTotalPrice = item.getUnitPrice().multiply(BigDecimal.valueOf(newQuantity));
            item.setTotalPrice(newTotalPrice);

        } else {
            if (request.getQuantity() > product.data().availableQuantity())
                throw new IllegalArgumentException("Item out of stock");


            cart.getCartItems().add(mapper.fromProductResponse(product.data(), request.getQuantity()));
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

    private Cart createNewCart(Long userId) {
        Cart newCart = Cart.builder()
                .userId(userId)
                .totalPrice(BigDecimal.ZERO)
                .cartItems(new ArrayList<>())
                .build();

        return repository.save(newCart);
    }
}
