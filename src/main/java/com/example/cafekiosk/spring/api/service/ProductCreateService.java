package com.example.cafekiosk.spring.api.service;

import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductCreateService {

    private final ProductRepository productRepository;

    public ProductDetailInfo create(ProductCreateRequest request) {
        Product product = Product.create(request.getType(), request.getName(), request.getPrice());
        productRepository.save(product);

        return ProductDetailInfo.of(product);
    }
}
