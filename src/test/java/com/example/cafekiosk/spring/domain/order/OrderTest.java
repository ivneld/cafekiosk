package com.example.cafekiosk.spring.domain.order;

import static org.assertj.core.api.Assertions.assertThat;

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
        // given
        Product product1 = createTestProduct(3000);
        Product product2 = createTestProduct(3500);
        Product product3 = createTestProduct(4000);

        List<Product> products = List.of(product1, product2, product3);

        int totalPrice = product1.getPrice() + product2.getPrice() + product3.getPrice();

        // when
        Order order = Order.create(products);

        // then
        assertThat(order.getTotalPrice()).isEqualTo(totalPrice);
    }

    @Test
    @DisplayName("주문 생성 시 8자리의 중복되지 않는 주문 번호가 생성된다.")
    void generateSerialNumber() {
        // given
        List<Product> products = List.of(
            createTestProduct(3000),
            createTestProduct(4000)
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
    void initOrderStatus() {
        // given
        List<Product> products = List.of(
            createTestProduct(3000),
            createTestProduct(4000)
        );

        // when
        Order order = Order.create(products);

        // then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.INIT);
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