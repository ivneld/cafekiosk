package com.example.cafekiosk.spring.api.service;

import com.example.cafekiosk.spring.domain.order.Order;
import com.example.cafekiosk.spring.domain.order.OrderRepository;
import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductRepository;
import com.example.cafekiosk.spring.domain.product.ProductSellingStatus;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderCreateService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public OrderResult createOrder(List<String> productNumbers) {
        productNumbers.forEach(productNumber -> {
            Optional<Product> optionalProduct = productRepository.findByProductNumber(productNumber);
            Product product = optionalProduct.orElseThrow(() ->
                new IllegalArgumentException("Invalid product number: " + productNumber));

            if (ProductSellingStatus.STOP_SELLING.equals(product.getSellingStatus())) {
                throw new IllegalStateException("Product status is STOP_SELLING");
            }
        });

        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

        Order order = Order.create(products);
        orderRepository.save(order);

        return OrderResult.of(order);
    }
}