package com.example.cafekiosk.spring.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductSellingStatus;
import com.example.cafekiosk.spring.domain.product.ProductType;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    @DisplayName("주문 생성 시 상품 리스트의 총 주문 금액을 계산한다.")
    void calculateTotalPrice() throws Exception {

    }

    @Test
    @DisplayName("주문 생성 시 8자리의 중복되지 않는 주문 번호가 생성된다.")
    void generateSerialNumber() {

    }

    @Test
    @DisplayName("주문 생성 시 초기 상태는 INIT 이다.")
    void initOrderStatus() {

    }

    private Product createTestProduct(int price) {
        return new Product(
            UUID.randomUUID().toString().substring(0, 8),
            ProductType.HANDMADE,
            ProductSellingStatus.SELLING,
            "TEST_PRODUCT",
            price
        );
    }
}