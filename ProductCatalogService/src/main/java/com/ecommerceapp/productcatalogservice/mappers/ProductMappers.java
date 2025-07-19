package com.ecommerceapp.productcatalogservice.mappers;

import com.ecommerceapp.productcatalogservice.dtos.ProductRequest;
import com.ecommerceapp.productcatalogservice.dtos.ProductResponse;
import com.ecommerceapp.productcatalogservice.product.Products;
import org.springframework.stereotype.Service;

@Service
public class ProductMappers {

    public Products toProduct(ProductRequest request) {
        return Products.builder()
                .productId(request.productId())
                .productName(request.productName())
                .productDescription(request.productDescription())
                .availableQuantity(request.availableQuantity())
                .itemImageUrl(request.itemImageUrl())
                .itemPrice(request.itemPrice())
                .productCategory(request.productCategory())
                .build();
    }

    public ProductResponse fromProduct(Products products) {
        return new ProductResponse(
                products.getProductId(),
                products.getProductName(),
                products.getProductDescription(),
                products.getAvailableQuantity(),
                products.getItemImageUrl(),
                products.getItemPrice(),
                products.getProductCategory()
        );
    }
}
