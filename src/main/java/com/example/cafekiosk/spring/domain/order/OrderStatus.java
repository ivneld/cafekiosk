package com.example.cafekiosk.spring.domain.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    INIT,
    CANCELED,
    PAYMENT_COMPLETED,
    PAYMENT_FAILED,
    RECEIVED,
    COMPLETED
}