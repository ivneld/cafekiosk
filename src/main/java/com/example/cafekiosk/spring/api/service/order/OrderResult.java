package com.example.cafekiosk.spring.api.service.order;

import java.util.List;
import lombok.Builder;

@Builder
public record OrderResult (
    String serialNumber,
    int totalPrice,
    List<String> productNumbers
) {

}