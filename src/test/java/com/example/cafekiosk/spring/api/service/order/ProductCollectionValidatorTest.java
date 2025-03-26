package com.example.cafekiosk.spring.api.service.order;

import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductSellingStatus;
import com.example.cafekiosk.spring.domain.product.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductCollectionValidatorTest {

    @Test
    @DisplayName("상품 번호 리스트로 존재하지 않는 상품 번호가 있는지 검증한다.")
    void validateExistence() throws Exception {

    }

    @Test
    @DisplayName("상품 번호 리스트로 판매 중지된 상품가 있는지 검증한다.")
    void validateStoppedSelling() throws Exception {

    }

    private Product createProduct(String productNumber, ProductSellingStatus status) {
        return new Product(productNumber, ProductType.HANDMADE, status, "TEST_NAME", 4000);
    }
}