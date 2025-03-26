package com.example.cafekiosk.spring.api.controller;

import static org.assertj.core.api.Assertions.*;

import com.example.cafekiosk.spring.api.service.order.OrderResult;
import com.example.cafekiosk.spring.domain.order.OrderHistoryRepository;
import com.example.cafekiosk.spring.domain.order.OrderRepository;
import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductRepository;
import com.example.cafekiosk.spring.domain.product.ProductType;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderCreateIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
        productRepository.deleteAll();
        orderHistoryRepository.deleteAll();
    }

    @Test
    @DisplayName("상품 번호로 주문을 생성한다.")
    void createOrder() {
        // given
        var americano = Product.create("001", ProductType.HANDMADE, "Americano", 3000);
        var latte = Product.create("002", ProductType.HANDMADE, "Latte", 3500);
        productRepository.saveAll(List.of(americano, latte));

        String requestUrl = "/api/v1/orders/new";
        List<String> productNumbers = List.of(americano.getProductNumber(), latte.getProductNumber());

        // when
        ResponseEntity<OrderResult> response = restTemplate.postForEntity(requestUrl, productNumbers, OrderResult.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(orderRepository.findAll()).isNotEmpty();
        assertThat(orderHistoryRepository.findAll()).isNotEmpty();
        assertThat(orderHistoryRepository.findAll().getFirst().isOrderSuccess()).isTrue();
    }

    @Test
    @DisplayName("존재하는 상품 정보로 주문을 생성할 수 없다.")
    void createOrderWithNotExistsProductNumber() {
        // given
        Product americano = Product.create(ProductType.HANDMADE, "Americano", 3000);
        Product latte = Product.create(ProductType.HANDMADE, "Latte", 3500);
        productRepository.saveAll(List.of(americano, latte));

        String requestUrl = "/api/v1/orders/new";
        List<String> productNumbers = List.of(americano.getProductNumber(), "XXX");

        // when
        ResponseEntity<OrderResult> response = restTemplate.postForEntity(requestUrl, productNumbers, OrderResult.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(orderRepository.findAll()).isEmpty();
        assertThat(orderHistoryRepository.findAll()).isNotEmpty(); // 질문 코드
        assertThat(orderHistoryRepository.findAll().getFirst().isOrderSuccess()).isFalse();
    }
}