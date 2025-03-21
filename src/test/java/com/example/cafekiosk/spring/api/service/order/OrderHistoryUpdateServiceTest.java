package com.example.cafekiosk.spring.api.service.order;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.cafekiosk.spring.domain.order.OrderHistory;
import com.example.cafekiosk.spring.domain.order.OrderHistoryRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class OrderHistoryUpdateServiceTest {

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;
    @Autowired
    private OrderHistoryUpdateService orderHistoryUpdateService;

    @Test
    void orderSuccess() throws Exception {
        // given
        OrderHistory orderHistory =
            OrderHistory.create(UUID.randomUUID().toString().substring(0, 8), List.of("001", "002", "003"));
        orderHistoryRepository.save(orderHistory);

        // when
        orderHistoryUpdateService.orderSuccess(orderHistory);

        // then
        assertThat(orderHistory.isOrderSuccess()).isTrue();
    }
}