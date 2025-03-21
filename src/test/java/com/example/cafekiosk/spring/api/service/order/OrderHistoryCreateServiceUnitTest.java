package com.example.cafekiosk.spring.api.service.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.when;

import com.example.cafekiosk.spring.domain.order.OrderHistory;
import com.example.cafekiosk.spring.domain.order.OrderHistoryRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderHistoryCreateServiceUnitTest {

    @InjectMocks
    private OrderHistoryCreateService orderHistoryCreateService;
    @Mock
    private OrderHistoryRepository orderHistoryRepository;

    @Test
    @DisplayName("주문 고유 번호와 상품 번호 리스트로 주문 이력을 저장한다.")
    void create() {
        // given
        String orderSerialNumber = UUID.randomUUID().toString().substring(0, 8);
        List<String> productNumbers = List.of("001", "002", "003");

        // stub
        when(orderHistoryRepository.existsByOrderSerialNumber(orderSerialNumber)).thenReturn(false);

        // when
        OrderHistory orderHistory = orderHistoryCreateService.create(orderSerialNumber, productNumbers);

        // then
        assertThat(orderHistory.getOrderSerialNumber()).isEqualTo(orderSerialNumber);
        assertThat(orderHistory.isOrderSuccess()).isFalse();
    }

    @Test
    @DisplayName("중복 주문 고유 번호로 이력 저장이 불가능하다.")
    void createWithDuplicatedOrderSerialNumber() {
        // given
        String orderSerialNumber = UUID.randomUUID().toString().substring(0, 8);
        List<String> productNumbers = List.of("001", "002", "003");

        // stub
        when(orderHistoryRepository.existsByOrderSerialNumber(orderSerialNumber)).thenReturn(true);

        // when, then
        assertThatCode(() -> orderHistoryCreateService.create(orderSerialNumber, productNumbers))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("Order serial number " + orderSerialNumber + " already exists");
    }
}