package com.ecommerceapp.shoppingcartservice.shoppingCart.cart;

import com.ecommerceapp.shoppingcartservice.shoppingCart.productClient.Data;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class CartMapper {

    public CartItem fromProductResponse(Data response, int quantity) {
        BigDecimal totalPrice = response.itemPrice().multiply(BigDecimal.valueOf(quantity));
        return new CartItem(
                response.productId(),
                response.productName(),
                response.productDescription(),
                quantity,
                response.itemPrice(),
                totalPrice
        );
    }
}
