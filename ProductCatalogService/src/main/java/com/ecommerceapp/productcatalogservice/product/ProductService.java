package com.ecommerceapp.productcatalogservice.product;

import com.ecommerceapp.productcatalogservice.dtos.ProductRequest;
import com.ecommerceapp.productcatalogservice.dtos.ProductResponse;
import com.ecommerceapp.productcatalogservice.response.ApiResponse;

import java.util.List;

public interface ProductService {

    ApiResponse<ProductResponse> addItem(ProductRequest request);

    ApiResponse<List<ProductResponse>> getAllProducts();

    ApiResponse<ProductResponse> getProductById(String productId);

    ApiResponse<String> deleteProduct(String productId);

    ApiResponse<String> updateProduct(ProductRequest request);
}
