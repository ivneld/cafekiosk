package com.example.cafekiosk.spring.api.service.product;

import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductSellingStatus;
import com.example.cafekiosk.spring.domain.product.ProductType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductDetailInfo {

    private String productNumber;
    private ProductType type;
    private ProductSellingStatus sellingStatus;
    private String name;
    private int price;

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
        return new ProductDetailInfo(
            product.getProductNumber(),
            product.getType(),
            product.getSellingStatus(),
            product.getName(),
            product.getPrice()
        );
    }
}
