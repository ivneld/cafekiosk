package com.example.cafekiosk.spring.api.controller;

import com.example.cafekiosk.spring.api.service.product.ProductCreateRequest;
import com.example.cafekiosk.spring.api.service.product.ProductCreateService;
import com.example.cafekiosk.spring.api.service.product.ProductDetailInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductCreateController {

    private final ProductCreateService productCreateService;

    @PostMapping("/api/v1/product")
    public ResponseEntity<ProductDetailInfo> create(@Valid @RequestBody ProductCreateRequest productCreateRequest) {
        ProductDetailInfo productDetailInfo = productCreateService.create(productCreateRequest);

        return ResponseEntity.ok(productDetailInfo);
    }
}
