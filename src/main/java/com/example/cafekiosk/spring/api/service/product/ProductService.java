package com.example.cafekiosk.spring.api.service.product;

import com.example.cafekiosk.spring.domain.Product;
import com.example.cafekiosk.spring.domain.ProductRepository;
import com.example.cafekiosk.spring.domain.ProductSellingStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getSellingProducts() {
        return productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());
    }
}
