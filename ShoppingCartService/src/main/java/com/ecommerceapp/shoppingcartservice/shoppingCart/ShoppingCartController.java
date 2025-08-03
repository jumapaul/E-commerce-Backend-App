package com.ecommerceapp.shoppingcartservice.shoppingCart;

import com.ecommerceapp.shoppingcartservice.shoppingCart.cart.Cart;
import com.ecommerceapp.shoppingcartservice.shoppingCart.cart.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService service;

    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse<Cart>> addToCart(
            @RequestBody CartItem cartItem,
            @PathVariable Long userId,
            @RequestHeader("Authorization") String authHeader
    ) {
        return ResponseEntity.ok(service.addProductToCart(cartItem, userId, authHeader));
    }

    @GetMapping("{userId}")
    public ResponseEntity<ApiResponse<Cart>> getUserCart(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getCart(userId));
    }

    @DeleteMapping("/{userId}/{itemId}")
    public ResponseEntity<ApiResponse<Cart>> removeFromCart(
            @PathVariable Long userId,
            @PathVariable String itemId
    ) {
        return ResponseEntity.ok(service.removeFromCart(userId, itemId));
    }

    @DeleteMapping("delete/{userId}")
    public ResponseEntity<ApiResponse<String>> deleteCart(
            @PathVariable(name = "userId") Long userId
    ) {
        return ResponseEntity.ok(service.deleteCart(userId));
    }
}
