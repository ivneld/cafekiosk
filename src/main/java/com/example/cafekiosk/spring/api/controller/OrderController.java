package com.example.cafekiosk.spring.api.controller;

import com.example.cafekiosk.spring.api.service.OrderCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderCreateService orderCreateService;

    @PostMapping("/api/v1/orders/new")
    public void createOrder() {

    }
}
