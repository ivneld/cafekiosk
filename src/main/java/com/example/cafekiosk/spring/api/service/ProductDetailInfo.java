package com.example.cafekiosk.spring.api.service;

import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductSellingStatus;
import com.example.cafekiosk.spring.domain.product.ProductType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductDetailInfo {

    private String productNumber;
    private ProductType type;
    private ProductSellingStatus sellingStatus;
    private String name;
    private int price;

    @Builder
    private ProductDetailInfo(
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

    public static ProductDetailInfo of(Product product) {
        return ProductDetailInfo.builder()
                                .productNumber(product.getProductNumber())
                                .type(product.getType())
                                .sellingStatus(product.getSellingStatus())
                                .name(product.getName())
                                .price(product.getPrice())
                                .build();
    }
}