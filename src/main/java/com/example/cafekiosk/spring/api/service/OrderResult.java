package com.example.cafekiosk.spring.api.service;

import com.example.cafekiosk.spring.domain.order.Order;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderResult {

    private String orderHistoryId;
    private int totalPrice;
    private LocalDateTime orderTime;
    private List<String> productNumbers;

    @Builder
    private OrderResult(String orderHistoryId, int totalPrice, LocalDateTime orderTime, List<String> productNumbers) {
        this.orderHistoryId = orderHistoryId;
        this.totalPrice = totalPrice;
        this.orderTime = orderTime;
        this.productNumbers = productNumbers;
    }

    public static OrderResult of(Order order) {
        return OrderResult.builder()
                          .orderHistoryId(order.getOrderHistoryId())
                          .totalPrice(order.getTotalPrice())
                          .orderTime(order.getCreatedDateTime())
                          .productNumbers(
                              order.getOrderProducts()
                                   .stream()
                                   .map(orderProduct -> orderProduct.getProduct().getProductNumber())
                                   .collect(Collectors.toList())
                          )
                          .build();
    }
}