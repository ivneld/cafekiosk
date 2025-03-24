package com.example.cafekiosk.spring.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    @DisplayName("새로운 상품을 생성 시 랜덤한 8자리 상품 번호가 발급되고, 초기 판매 상태는 HOLD 이다.")
    void createProductWithoutProductNumber() throws Exception {
        // when
        var americano = Product.create(ProductType.HANDMADE, "Americano", 3000);

        // then
        assertThat(americano.getProductNumber()).isNotEmpty();
        assertThat(americano.getProductNumber().length()).isEqualTo(8);
        assertThat(americano.getSellingStatus()).isEqualTo(ProductSellingStatus.HOLD);
    }

    @Test
    @DisplayName("직접 생성한 상품 번호를 통해 상품을 생성할 수 있다.")
    void createProductWithProductNumber() throws Exception {
        // given
        String productNumber = "001";

        // when
        Product americano = Product.create(productNumber, ProductType.HANDMADE, "Americano", 3000);

        // then
        assertThat(americano.getProductNumber()).isEqualTo(productNumber);
    }

    @Test
    @DisplayName("판매 상태가 HOLD 인 상품은 판매 시작 상태로 변경할 수 있다.")
    void startSelling() throws Exception {
        // given
        Product americano = Product.create(ProductType.HANDMADE, "Americano", 3000);

        // when
        americano.startSelling();

        // then
        assertThat(americano.getSellingStatus()).isEqualTo(ProductSellingStatus.SELLING);
    }

    @Test
    @DisplayName("판매 상태가 STOP_SELLING 인 상품은 판매 시작 상태로 변경할 수 없다.")
    void startSellingWithStoppedSelling() throws Exception {
        // given
        Product americano = Product.create(ProductType.HANDMADE, "Americano", 3000);
        americano.stopSelling();

        // when, then
        assertThatCode(americano::startSelling)
            .isExactlyInstanceOf(IllegalStateException.class)
            .hasMessage("판매 중지된 상품은 판매 재개할 수 없습니다.");
    }

    @Test
    @DisplayName("판매 상태가 STOP 인 상품은 판매 보류 상태로 변경할 수 있다.")
    void stopSelling() throws Exception {
        // given
        Product americano = Product.create(ProductType.HANDMADE, "Americano", 3000);
        americano.startSelling();

        // when
        americano.hold();

        // then
        assertThat(americano.getSellingStatus()).isEqualTo(ProductSellingStatus.HOLD);
    }
}