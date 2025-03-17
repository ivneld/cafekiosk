package com.example.cafekiosk.spring.api.controller;

import com.example.cafekiosk.spring.domain.Product;
import com.example.cafekiosk.spring.domain.ProductSellingStatus;
import com.example.cafekiosk.spring.domain.ProductType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponse {

    private String productNumber;
    private ProductType type;
    private ProductSellingStatus sellingStatus;
    private String name;
    private int price;

    @Builder
    private ProductResponse(
        String productNumber,
        ProductType type,
        ProductSellingStatus sellingStatus,
        String name,
        int price) {
        this.productNumber = productNumber;
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
                              .productNumber(product.getProductNumber())
                              .type(product.getType())
                              .sellingStatus(product.getSellingStatus())
                              .name(product.getName())
                              .price(product.getPrice())
                              .build();
    }
}
