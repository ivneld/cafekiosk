package com.example.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    @DisplayName("새로운 상품을 생성 시 랜덤한 8자리 상품 번호가 발급되고, 초기 판매 상태는 HOLD 이다.")
    void createProductWithoutProductNumber() throws Exception {

    }

    @Test
    @DisplayName("직접 생성한 상품 번호를 통해 상품을 생성할 수 있다.")
    void createProductWithProductNumber() throws Exception {

    }

    @Test
    @DisplayName("판매 상태가 HOLD 인 상품은 판매 시작 상태로 변경할 수 있다.")
    void startSelling() throws Exception {

    }

    @Test
    @DisplayName("판매 상태가 STOP_SELLING 인 상품은 판매 시작 상태로 변경할 수 없다.")
    void startSellingWithStoppedSelling() throws Exception {

    }

    @Test
    @DisplayName("판매 상태가 STOP 인 상품은 판매 보류 상태로 변경할 수 있다.")
    void stopSelling() throws Exception {

    }
}