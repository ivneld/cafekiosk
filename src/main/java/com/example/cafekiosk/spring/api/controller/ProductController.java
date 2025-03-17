package com.example.cafekiosk.spring.api.controller;

import com.example.cafekiosk.spring.api.service.product.ProductService;
import com.example.cafekiosk.spring.domain.Product;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/api/v1/products/selling")
    public ResponseEntity<List<ProductResponse>> getSellingProducts() {
        List<Product> products = productService.getSellingProducts();

        List<ProductResponse> response = products.stream()
                                             .map(ProductResponse::of)
                                             .toList();
        return ResponseEntity.ok(response);
    }
}
