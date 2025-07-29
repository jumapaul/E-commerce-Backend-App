package com.ecommerceapp.shoppingcartservice.shoppingCart;

import com.ecommerceapp.shoppingcartservice.shoppingCart.cart.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShoppingCartRepository extends MongoRepository<Cart, Long> {
}
