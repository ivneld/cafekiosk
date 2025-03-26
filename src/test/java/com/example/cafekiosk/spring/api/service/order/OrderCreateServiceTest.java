package com.example.cafekiosk.spring.api.service.order;

import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductSellingStatus;
import com.example.cafekiosk.spring.domain.product.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderCreateServiceTest {

    @Test
    @DisplayName("상품 번호 리스트로 주문을 생성한다.")
    void createOrder() throws Exception {

    }

    @Test
    @DisplayName("존재하지 않는 상품 번호가 주문에 포함되어 있는 경우 실패한다.")
    void createOrderWithNonexistentProductNumber() throws Exception {

    }

    @Test
    @DisplayName("판매 중지된 상품 번호가 주문에 포함되어 있는 경우 실패한다.")
    void createOrderWithStoppedSellingProductNumber() throws Exception {

    }

    private Product createProduct(String productNumber, ProductSellingStatus status) {
        return new Product(productNumber, ProductType.HANDMADE, status, "TEST_NAME", 4000);
    }
}