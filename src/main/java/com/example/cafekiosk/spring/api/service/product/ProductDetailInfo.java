package com.example.cafekiosk.spring.api.service.product;

import com.example.cafekiosk.spring.domain.product.ProductSellingStatus;
import com.example.cafekiosk.spring.domain.product.ProductType;
import lombok.Builder;

@Builder
public record ProductDetailInfo(
    String productNumber,
    ProductType type,
    ProductSellingStatus sellingStatus,
    String name,
    int price
) {

}