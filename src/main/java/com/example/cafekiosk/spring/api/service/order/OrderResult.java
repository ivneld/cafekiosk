package com.example.cafekiosk.spring.api.service.order;

import com.example.cafekiosk.spring.domain.order.Order;
import com.example.cafekiosk.spring.domain.orderproduct.OrderProduct;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderResult {

    private String serialNumber;
    private int totalPrice;
    private List<String> productNumbers;

    private OrderResult(String serialNumber, int totalPrice, List<OrderProduct> orderProducts) {
        this.serialNumber = serialNumber;
        this.totalPrice = totalPrice;
        this.productNumbers = orderProducts.stream()
                                           .map(orderProduct -> orderProduct.getProduct().getProductNumber())
                                           .toList();
    }

    public static OrderResult of(Order order) {
        return new OrderResult(
            order.getSerialNumber(),
            order.getTotalPrice(),
            order.getOrderProducts()
        );
    }
}