package com.ecommerceapp.productcatalogservice.product;

import com.ecommerceapp.productcatalogservice.dtos.ProductRequest;
import com.ecommerceapp.productcatalogservice.dtos.ProductResponse;
import com.ecommerceapp.productcatalogservice.exception.ResourceNotFoundException;
import com.ecommerceapp.productcatalogservice.mappers.ProductMappers;
import com.ecommerceapp.productcatalogservice.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductMappers mappers;
    private final ProductRepository repository;

    @Override
    public ApiResponse<ProductResponse> addItem(@Valid ProductRequest request) {
        var product = repository.save(mappers.toProduct(request));
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Product added successfully",
                mappers.fromProduct(product)
        );
    }

    @Override
    public ApiResponse<List<ProductResponse>> getAllProducts() {
        try {
            var data = repository.findAll().stream().map(mappers::fromProduct).toList();

            return new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Products retrieved successfully",
                    data
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiResponse<ProductResponse> getProductById(String productId) {
        var data = repository.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Product with id " + productId + " not found")
        );

        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Product retrieved successfully",
                mappers.fromProduct(data)
        );
    }

    @Override
    public ApiResponse<String> deleteProduct(String productId) {

        repository.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Product with id " + productId + " not found")
        );
        repository.deleteById(productId);

        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Item deleted successfully",
                null
        );
    }

    @Override
    public ApiResponse<String> updateProduct(@Valid ProductRequest request) {
        repository.findById(request.productId()).orElseThrow(() ->
                new ResourceNotFoundException("Product with id " + request.productId() + " not found")
        );

        repository.save(mappers.toProduct(request));

        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Item updated successfully",
                null
        );
    }
}
