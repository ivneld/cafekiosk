package com.example.cafekiosk.spring.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

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

    @DisplayName("상품 판매 중지 이후 재개 시나리오")
    @TestFactory
    Collection<DynamicTest> startSellingDynamicTest() {
        // given
        Product americano = Product.create(ProductType.HANDMAND, "Americano", 3000);

        return List.of(
            DynamicTest.dynamicTest("상품을 판매 중지한다.", () -> {
                // when
                americano.stopSelling();

                // then
                assertThat(americano.getSellingStatus()).isEqualTo(ProductSellingStatus.STOP_SELLING);
            }),
            DynamicTest.dynamicTest("판매 중지된 상품은 다시 판매할 수 없습니다.", () -> {
                // when, then
                assertThatThrownBy(americano::startSelling)
                    .isExactlyInstanceOf(IllegalStateException.class)
                    .hasMessage("해당 상품은 판매 중지된 상품입니다.");
            })
        );
    }
}