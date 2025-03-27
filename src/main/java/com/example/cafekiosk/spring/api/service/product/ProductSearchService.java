package com.example.cafekiosk.spring.api.service.product;

import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductRepository;
import com.example.cafekiosk.spring.domain.product.ProductSellingStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductSearchService {

    private final ProductRepository productRepository;

    public List<ProductDetailInfo> findSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(List.of(ProductSellingStatus.SELLING));
        return products.stream()
                       .map(ProductDetailInfo::of)
                       .toList();
    }
}