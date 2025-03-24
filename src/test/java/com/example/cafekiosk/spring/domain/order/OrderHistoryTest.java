package com.example.cafekiosk.spring.domain.order;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

class OrderHistoryTest {

    @Test
    @DisplayName("주문 이력을 생성하면 주문 성공 여부가 false 상태이다.")
    void isOrderSuccess() throws Exception {
        // given
        String orderSerialNumber = UUID.randomUUID().toString().substring(0, 8);
        List<String> productNumbers = List.of("001", "002", "003");

        // when
        OrderHistory orderHistory = OrderHistory.create(orderSerialNumber, productNumbers);

        // when
        assertThat(orderHistory.isOrderSuccess()).isFalse();
    }

    @Test
    @DisplayName("주문 이력 생성 시 상품 번호를 JSON 형태로 변환한다.")
    void productNumbersToJson() throws Exception {
        // given
        String orderSerialNumber = UUID.randomUUID().toString().substring(0, 8);
        List<String> productNumbers = List.of("001", "002", "003");
        String jsonProductNumbers = new ObjectMapper().writeValueAsString(productNumbers);

        // when
        OrderHistory orderHistory = OrderHistory.create(orderSerialNumber, productNumbers);

        // then
        assertThat(orderHistory.getProductNumbers()).isEqualTo(jsonProductNumbers);
    }

    @Test
    @DisplayName("주문 성공 시 주문 이력에 성공 여부를 변경한다.")
    void onOrderSuccess() {
        // given
        String orderSerialNumber = UUID.randomUUID().toString().substring(0, 8);
        List<String> productNumbers = List.of("001", "002", "003");

        OrderHistory orderHistory = OrderHistory.create(orderSerialNumber, productNumbers);

        // when
        orderHistory.onOrderSuccess();

        // then
        assertThat(orderHistory.isOrderSuccess()).isTrue();
    }
}