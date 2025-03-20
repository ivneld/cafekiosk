package com.example.cafekiosk.spring.api.controller;

import com.example.cafekiosk.spring.api.service.ProductDetailInfo;
import com.example.cafekiosk.spring.api.service.ProductSearchService;
import com.example.cafekiosk.spring.domain.product.Product;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductSearchController {

    private final ProductSearchService productSearchService;

    @GetMapping("/api/v1/products/selling")
    public ResponseEntity<List<ProductDetailInfo>> getSellingProducts() {
        List<ProductDetailInfo> sellingProducts = productSearchService.getSellingProducts();

        return ResponseEntity.ok(sellingProducts);
    }
}