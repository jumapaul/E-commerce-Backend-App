package com.ecommerceapp.productcatalogservice.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Products {

    @Id
    private String productId;
    private String productName;
    private String productDescription;
    private int availableQuantity;
    private String itemImageUrl;
    private double itemPrice;
    private String productCategory;
}
