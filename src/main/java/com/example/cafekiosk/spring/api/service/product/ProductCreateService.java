package com.example.cafekiosk.spring.api.service.product;

import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductCreateService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductDetailInfo create(ProductCreateRequest request) {
        var product = Product.create(request.getType(), request.getName(), request.getPrice());
        productRepository.save(product);

        return ProductDetailInfo.of(product);
    }
}
