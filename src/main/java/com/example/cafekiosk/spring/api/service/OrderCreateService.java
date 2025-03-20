package com.example.cafekiosk.spring.api.service;

import com.example.cafekiosk.spring.domain.order.Order;
import com.example.cafekiosk.spring.domain.order.OrderHistory;
import com.example.cafekiosk.spring.domain.order.OrderRepository;
import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductRepository;
import com.example.cafekiosk.spring.domain.product.ProductSellingStatus;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderCreateService {

    private final OrderHistoryCreateService historyLoggingService;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderResult createOrder(List<String> productNumbers) {
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

        Order order = Order.create(products);

        OrderHistory orderHistory = historyLoggingService.create(order.getSerialNumber(), productNumbers);

        List<String> missingProductNumbers = getMissingProductNumbers(products, productNumbers);
        if (!missingProductNumbers.isEmpty()) {
            throw new IllegalArgumentException("Invalid product numbers: " + missingProductNumbers);
        }

        List<String> stoppedSellingProductNumbers = getStoppedSellingProductNumbers(products);
        if (!stoppedSellingProductNumbers.isEmpty()) {
            throw new IllegalArgumentException("Product status is STOP_SELLING. Product numbers: " + stoppedSellingProductNumbers);
        }

        orderRepository.save(order);
        orderHistory.onOrderSuccess();

        return OrderResult.of(order);
    }

    private List<String> getStoppedSellingProductNumbers(List<Product> products) {
        Map<String, ProductSellingStatus> statusMap = products.stream()
                                                              .collect(Collectors.toMap(
                                                                  Product::getProductNumber,
                                                                  Product::getSellingStatus
                                                              ));
        return statusMap.entrySet().stream()
                        .filter(entry -> ProductSellingStatus.STOP_SELLING.equals(entry.getValue()))
                        .map(Map.Entry::getKey)
                        .toList();
    }

    private List<String> getMissingProductNumbers(List<Product> products, List<String> expectedProductNumbers) {
        List<String> findProductNumbers = products.stream().map(Product::getProductNumber).toList();
        return expectedProductNumbers.stream()
                                     .filter(productNumber -> !findProductNumbers.contains(productNumber))
                                     .toList();
    }
}