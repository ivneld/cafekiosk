package com.example.cafekiosk.spring.api.service.order;

import com.example.cafekiosk.spring.domain.order.OrderHistory;
import com.example.cafekiosk.spring.domain.order.OrderHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
class OrderHistoryUpdateService {

    private final OrderHistoryRepository orderHistoryRepository;

    public void orderSuccess(OrderHistory orderHistory) {
        orderHistory.onOrderSuccess();
        orderHistoryRepository.save(orderHistory);
    }
}
