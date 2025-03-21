package com.example.cafekiosk.spring.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.cafekiosk.spring.domain.order.OrderHistory;
import com.example.cafekiosk.spring.domain.order.OrderHistoryRepository;
import com.example.cafekiosk.spring.domain.order.OrderRepository;
import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductRepository;
import com.example.cafekiosk.spring.domain.product.ProductSellingStatus;
import com.example.cafekiosk.spring.domain.product.ProductType;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class OrderCreateServiceTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @Autowired
    private OrderCreateService orderCreateService;

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
        productRepository.deleteAll();
        orderHistoryRepository.deleteAll();
    }

    @Test
    @DisplayName("상품번호 리스트를 받아 주문을 생성한다.")
    void createOrder() {
        // given
        Product product1 = createProduct("001", ProductSellingStatus.SELLING);
        Product product2 = createProduct("002", ProductSellingStatus.HOLD);
        Product product3 = createProduct("003", ProductSellingStatus.SELLING);
        productRepository.saveAll(List.of(product1, product2, product3));

        List<String> productNumbers =
            List.of(product1.getProductNumber(), product2.getProductNumber(), product3.getProductNumber());

        // when
        OrderResult orderResult = orderCreateService.createOrder(productNumbers);

        // then
        assertThat(orderRepository.findBySerialNumber(orderResult.getSerialNumber())).isPresent();

        Optional<OrderHistory> optionalOrderHistory = orderHistoryRepository.findByOrderSerialNumber(orderResult.getSerialNumber());
        assertThat(optionalOrderHistory.isPresent()).isTrue();

        OrderHistory orderHistory = optionalOrderHistory.get();
        assertThat(orderHistory.isOrderSuccess()).isTrue();
    }

    @Test
    @DisplayName("존재하지 않는 상품번호가 주문에 포함되어 있는 경우 실패한다.")
    void createOrderWithInvalidProductNumber() {
        // given
        Product product1 = createProduct("001", ProductSellingStatus.SELLING);
        Product product2 = createProduct("002", ProductSellingStatus.HOLD);
        productRepository.saveAll(List.of(product1, product2));

        // when, then
        assertThatThrownBy(() -> orderCreateService.createOrder(List.of("003", "004")))
            .isInstanceOf(IllegalArgumentException.class);

        List<OrderHistory> orderHistoryList = orderHistoryRepository.findAll();
        assertThat(orderHistoryList.size()).isEqualTo(1);

        assertThat(orderHistoryList.getFirst().isOrderSuccess()).isFalse();
    }

    @Test
    @DisplayName("판매하지 않고 있는 상품번호가 주문에 포함되어 있는 경우 실패한다.")
    void createOrderWithStopSellingProductNumber() {
        // given
        Product product1 = createProduct("001", ProductSellingStatus.SELLING);
        Product product2 = createProduct("002", ProductSellingStatus.STOP_SELLING);
        Product product3 = createProduct("003", ProductSellingStatus.STOP_SELLING);
        productRepository.saveAll(List.of(product1, product2, product3));

        List<String> productNumbers =
            List.of(product1.getProductNumber(), product2.getProductNumber(), product3.getProductNumber());

        // when, then
        assertThatCode(() -> orderCreateService.createOrder(productNumbers))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("Product status is STOP_SELLING. Product numbers: [002, 003]");

        List<OrderHistory> orderHistoryList = orderHistoryRepository.findAll();
        assertThat(orderHistoryList.size()).isEqualTo(1);

        assertThat(orderHistoryList.getFirst().isOrderSuccess()).isFalse();
    }

    private Product createProduct(String productNumber, ProductSellingStatus status) {
        return Product.builder()
                      .productNumber(productNumber)
                      .type(ProductType.HANDMAND)
                      .name("TEST_NAME")
                      .sellingStatus(status)
                      .price(4000)
                      .build();
    }
}