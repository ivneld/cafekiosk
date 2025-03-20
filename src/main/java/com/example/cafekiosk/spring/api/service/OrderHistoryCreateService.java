package com.example.cafekiosk.spring.api.service;

import com.example.cafekiosk.spring.domain.order.OrderHistory;
import com.example.cafekiosk.spring.domain.order.OrderHistoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
class OrderHistoryCreateService {

    private final OrderHistoryRepository orderHistoryRepository;

    public OrderHistory create(String orderSerialNumber, List<String> productNumbers) {
        if (orderHistoryRepository.existsByOrderSerialNumber(orderSerialNumber)) {
            throw new IllegalArgumentException("Order serial number " + orderSerialNumber + " already exists");
        }

        var orderHistory = OrderHistory.create(orderSerialNumber, productNumbers);
        orderHistoryRepository.save(orderHistory);

        return orderHistory;
    }
}