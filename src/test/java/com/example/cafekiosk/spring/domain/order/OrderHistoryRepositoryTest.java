package com.example.cafekiosk.spring.domain.order;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
class OrderHistoryRepositoryTest {

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @Test
    @DisplayName("주문 번호로 저장된 주문 이력이 존재하는지 확인한다.")
    void existsByOrderSerialNumber() throws Exception {
        // given
        String orderSerialNumber = UUID.randomUUID().toString().substring(0, 8);
        String productNumbers = "[\"001\",\"002\",\"003\"]";

        orderHistoryRepository.saveAndFlush(new OrderHistory(orderSerialNumber, productNumbers, false));

        // when
        boolean isExists = orderHistoryRepository.existsByOrderSerialNumber(orderSerialNumber);

        // then
        assertThat(isExists).isTrue();
    }
}