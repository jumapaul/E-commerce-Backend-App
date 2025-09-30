package com.ecommerceapp.shoppingcartservice.shoppingCart;

import com.ecommerceapp.shoppingcartservice.shoppingCart.cart.Cart;
import com.ecommerceapp.shoppingcartservice.shoppingCart.cart.CartItem;
import com.ecommerceapp.shoppingcartservice.shoppingCart.cart.CartItemRequest;

public interface ShoppingCartService {

   ApiResponse<Cart> addProductToCart(CartItemRequest request, Long userId);

   ApiResponse<Cart> getCart(Long userId);

   ApiResponse<Cart> removeFromCart(Long userId, String itemId);

   ApiResponse<String> clearCart(Long userId);

//   Cart updateItem(Long userId, String itemId, int quantity);
}
