package com.example.cafekiosk.spring.api.service.order;

import com.example.cafekiosk.spring.domain.order.Order;
import com.example.cafekiosk.spring.domain.orderproduct.OrderProduct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderResult {

    private String serialNumber;
    private int totalPrice;
    private LocalDateTime orderTime;
    private List<String> productNumbers;

    private OrderResult(
        String serialNumber,
        int totalPrice,
        LocalDateTime orderTime,
        List<OrderProduct> orderProducts) {
        this.serialNumber = serialNumber;
        this.totalPrice = totalPrice;
        this.orderTime = orderTime;
        this.productNumbers = orderProducts.stream()
                                           .map(orderProduct -> orderProduct.getProduct().getProductNumber())
                                           .collect(Collectors.toList());
    }

    public static OrderResult of(Order order) {
        return new OrderResult(
            order.getSerialNumber(),
            order.getTotalPrice(),
            order.getCreatedDateTime(),
            order.getOrderProducts()
        );
    }
}