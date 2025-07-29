package com.ecommerceapp.shoppingcartservice.shoppingCart;

import com.ecommerceapp.shoppingcartservice.shoppingCart.cart.Cart;
import com.ecommerceapp.shoppingcartservice.shoppingCart.cart.CartItem;

public interface ShoppingCartService {

   ApiResponse<Cart> addProductToCart(CartItem request, Long userId);

   ApiResponse<Cart> getCart(Long userId);

   ApiResponse<Cart> removeFromCart(Long userId, String itemId);

//   Cart updateItem(Long userId, String itemId, int quantity);
}
