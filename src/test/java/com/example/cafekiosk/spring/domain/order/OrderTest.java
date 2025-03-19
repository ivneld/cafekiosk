package com.example.cafekiosk.spring.domain.order;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductSellingStatus;
import com.example.cafekiosk.spring.domain.product.ProductType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    @DisplayName("주문 생성 시 상품 리스트의 총 주문 금액을 계산한다.")
    void calculateTotalPrice() throws Exception {
        // given
        List<Product> products = List.of(
            createProduct("001", 3000),
            createProduct("002", 2000),
            createProduct("003", 1000)
        );

        // when
        Order order = Order.create(products);

        // then
        assertThat(order.getTotalPrice()).isEqualTo(6000);
    }

    @Test
    @DisplayName("주문 생성 시 8 자리의 중복되지 않는 고유 번호가 생성된다.")
    void generateSerialNumber() {
        // given
        List<Product> products = List.of(
            createProduct("001", 1000),
            createProduct("002", 2000)
        );

        // when
        Order order1 = Order.create(products);
        Order order2 = Order.create(products);

        // then
        assertThat(order1.getSerialNumber().length()).isEqualTo(8);
        assertThat(order2.getSerialNumber().length()).isEqualTo(8);
        assertThat(order1.getSerialNumber()).isNotEqualTo(order2.getSerialNumber());
    }

    @Test
    @DisplayName("주문 생성 시 초기 상태는 INIT 이다.")
    void initOrderStatus() throws Exception {
        // given
        List<Product> products = List.of(
            createProduct("001", 1000),
            createProduct("002", 2000)
        );

        // when
        Order order = Order.create(products);

        // then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.INIT);
    }

    private Product createProduct(String productNumber, int price) {
        return Product.builder()
                      .productNumber(productNumber)
                      .type(ProductType.HANDMAND)
                      .name("TEST_NAME")
                      .sellingStatus(ProductSellingStatus.SELLING)
                      .price(price)
                      .build();
    }
}