package com.example.cafekiosk.spring.api.service.order;

import com.example.cafekiosk.spring.domain.order.Order;
import com.example.cafekiosk.spring.domain.order.OrderHistory;
import com.example.cafekiosk.spring.domain.order.OrderRepository;
import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderCreateService {

    private final OrderHistoryCreateService orderHistoryCreateService;
    private final OrderHistoryUpdateService orderHistoryUpdateService;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public OrderResult createOrder(List<String> productNumbers) {
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

        var order = Order.create(products);

        OrderHistory orderHistory = orderHistoryCreateService.register(order.getSerialNumber(), productNumbers);

        ProductCollectionValidator validator = new ProductCollectionValidator(products);
        // 존재하지 않는 상품 번호 검증
        validator.validateExistence(productNumbers);
        // 판매 중지된 상품 번호 검증
        validator.validateStoppedSelling();

        orderRepository.save(order);
        orderHistoryUpdateService.orderSuccess(orderHistory);

        return OrderResult.of(order);
    }
}