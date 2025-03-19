package com.example.cafekiosk.spring.api.service;

import static org.assertj.core.api.Assertions.assertThatCode;

import com.example.cafekiosk.spring.domain.order.OrderHistory;
import com.example.cafekiosk.spring.domain.order.OrderHistoryRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class OrderHistoryLoggingServiceTest {

    @Autowired
    private OrderHistoryLoggingService service;
    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @Test
    @DisplayName("주문 번호와 상품 번호 리스트로 상품 번호 리스트의 개수 만큼 이력 저장을 한다.")
    void register() throws Exception {
        // given
        List<String> productNumbers = List.of("001", "002", "003");
        String orderSerialNumber = UUID.randomUUID().toString().substring(0, 8);

        // when
        service.register(orderSerialNumber, productNumbers);

        // then
        List<OrderHistory> orderHistories = orderHistoryRepository.findByOrderSerialNumber(orderSerialNumber);
    }

    @Test
    @DisplayName("중복된 주문 번호에 대한 이력 저장 요청은 존재할 수 없다.")
    void registerWithDuplicatedOrderSerialNumber() throws Exception {
        // given
        List<String> firstProductNumbers = List.of("001", "002");
        List<String> secondProductNumbers = List.of("003", "004");

        String orderSerialNumber = UUID.randomUUID().toString().substring(0, 8);

        service.register(orderSerialNumber, firstProductNumbers);

        // when, then
        assertThatCode(() -> service.register(orderSerialNumber, secondProductNumbers))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("Serial number already exists, serial number: " + orderSerialNumber);
    }
}