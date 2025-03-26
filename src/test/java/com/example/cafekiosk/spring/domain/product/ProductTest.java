package com.example.cafekiosk.spring.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    @DisplayName("상품 생성 시 8자리 상품 고유 번호가 발급되고, 판매 대기 상태가 된다.")
    void createProduct() {
        // when
        Product americano = Product.create(ProductType.HANDMAND, "Americano", 3000);
        Product latte = Product.create(ProductType.HANDMAND, "Latte", 4000);

        // then
        assertThat(americano.getSellingStatus()).isEqualTo(ProductSellingStatus.HOLD);
        assertThat(latte.getSellingStatus()).isEqualTo(ProductSellingStatus.HOLD);
        assertThat(americano.getProductNumber()).isNotEqualTo(latte.getProductNumber());
    }
}