package com.example.cafekiosk.spring.api.controller;

import com.example.cafekiosk.spring.api.service.OrderCreateService;
import com.example.cafekiosk.spring.api.service.OrderResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderCreateService orderCreateService;

    @PostMapping("/api/v1/orders/new")
    public ResponseEntity<OrderResult> createOrder(@RequestBody List<String> productNumbers) {
        OrderResult orderResult = orderCreateService.createOrder(productNumbers);

        return ResponseEntity.ok(orderResult);
    }
}