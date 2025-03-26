package com.example.cafekiosk.spring.api.service.order;

import com.example.cafekiosk.spring.domain.order.OrderHistory;
import java.util.List;

public class OrderHistoryCreateService {

    public OrderHistory register(String orderSerialNumber, List<String> productNumbers) {
        // TODO : orderSerialNumber 중복 조회

        // TODO : OrderHistory 생성 및 저장

        return new OrderHistory(orderSerialNumber, "001", true);
    }
}