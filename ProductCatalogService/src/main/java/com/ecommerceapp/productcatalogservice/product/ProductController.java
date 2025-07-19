package com.ecommerceapp.productcatalogservice.product;

import com.ecommerceapp.productcatalogservice.dtos.ProductRequest;
import com.ecommerceapp.productcatalogservice.dtos.ProductResponse;
import com.ecommerceapp.productcatalogservice.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    ResponseEntity<ApiResponse<ProductResponse>> addProducts(@RequestBody @Valid ProductRequest request) {

        return ResponseEntity.ok(productService.addItem(request));
    }

    @GetMapping
    ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{productId}")
    ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable String productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @DeleteMapping("/{productId}")
    ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable String productId) {
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }

    @PutMapping
    ResponseEntity<ApiResponse<String>> updateProduct(@RequestBody @Valid ProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(request));
    }
}
