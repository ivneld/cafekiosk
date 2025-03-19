package com.example.cafekiosk.spring.domain.order;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        String jsonProductNumbers = StringListJsonConverter.listToJson(productNumbers);

        // when
        OrderHistory orderHistory = OrderHistory.create(orderSerialNumber, productNumbers);

        // then
        assertThat(orderHistory.getProductNumbers()).isEqualTo(jsonProductNumbers);
    }
}