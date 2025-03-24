package com.example.cafekiosk.spring.domain.order;

import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductSellingStatus;
import com.example.cafekiosk.spring.domain.product.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    @DisplayName("주문 생성 시 상품 리스트의 총 주문 금액을 계산한다.")
    void calculateTotalPrice() throws Exception {
        // given


        // when

        // then

    }

    private Product createTestProduct(String productNumber, ProductSellingStatus sellingStatus) {
        return new Product(productNumber, ProductType.HANDMADE, sellingStatus, "TEST_PRODUCT", 3000);
    }
}