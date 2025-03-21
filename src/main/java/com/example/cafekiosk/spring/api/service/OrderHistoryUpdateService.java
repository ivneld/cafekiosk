package com.example.cafekiosk.spring.api.service;

import com.example.cafekiosk.spring.domain.order.OrderHistory;
import com.example.cafekiosk.spring.domain.order.OrderHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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