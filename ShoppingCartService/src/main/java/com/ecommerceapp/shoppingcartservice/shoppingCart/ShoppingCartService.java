package com.ecommerceapp.shoppingcartservice.shoppingCart;

import com.ecommerceapp.shoppingcartservice.shoppingCart.cart.Cart;
import com.ecommerceapp.shoppingcartservice.shoppingCart.cart.CartItem;

public interface ShoppingCartService {

   ApiResponse<Cart> addProductToCart(CartItem request, Long userId, String authHeader);

   ApiResponse<Cart> getCart(Long userId);

   ApiResponse<Cart> removeFromCart(Long userId, String itemId);

   ApiResponse<String> deleteCart(Long userId);

//   Cart updateItem(Long userId, String itemId, int quantity);
}
