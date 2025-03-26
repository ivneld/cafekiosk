package com.example.cafekiosk.spring.api.service.order;

import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductSellingStatus;
import java.util.List;

class ProductCollectionValidator {
    private final List<Product> existenceProducts;

    public ProductCollectionValidator(List<Product> existenceProducts) {
        this.existenceProducts = existenceProducts;
    }

    public void validateExistence(List<String> productNumbers) {
        List<String> existenceProductNumbers = existenceProducts.stream().map(Product::getProductNumber).toList();

        List<String> missingProductNumbers = productNumbers.stream()
                                                           .filter(productNumber -> !existenceProductNumbers.contains(productNumber))
                                                           .toList();

        if (!missingProductNumbers.isEmpty()) {
            throw new IllegalArgumentException("Nonexistent product numbers: " + missingProductNumbers);
        }
    }

    public void validateStoppedSelling() {
        List<String> stoppedSellingProductNumbers = existenceProducts.stream()
                                                                     .filter(product -> ProductSellingStatus.STOP_SELLING.equals(product.getSellingStatus()))
                                                                     .map(Product::getProductNumber)
                                                                     .toList();

        if (!stoppedSellingProductNumbers.isEmpty()) {
            throw new IllegalArgumentException("Stopped selling product numbers: " + stoppedSellingProductNumbers);
        }
    }
}