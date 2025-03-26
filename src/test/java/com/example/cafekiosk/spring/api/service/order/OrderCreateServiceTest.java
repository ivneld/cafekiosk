package com.example.cafekiosk.spring.api.service.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

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

@SpringBootTest
class OrderCreateServiceTest {

    @Autowired
    private OrderCreateService orderCreateService;

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
    @DisplayName("상품 번호 리스트로 주문을 생성한다.")
    void createOrder() throws Exception {
        // given
        Product product1 = createProduct("001", ProductSellingStatus.SELLING);
        Product product2 = createProduct("002", ProductSellingStatus.HOLD);
        Product product3 = createProduct("003", ProductSellingStatus.SELLING);
        productRepository.saveAllAndFlush(List.of(product1, product2, product3));

        List<String> productNumbers =
            List.of(product1.getProductNumber(), product2.getProductNumber(), product3.getProductNumber());

        // when
        OrderResult orderResult = orderCreateService.createOrder(productNumbers);

        // then
        assertThat(orderRepository.findBySerialNumber(orderResult.getSerialNumber())).isPresent();

        Optional<OrderHistory> optionalOrderHistory = orderHistoryRepository.findByOrderSerialNumber(orderResult.getSerialNumber());
        assertThat(optionalOrderHistory).isPresent();

        OrderHistory orderHistory = optionalOrderHistory.get();
        assertThat(orderHistory.isOrderSuccess()).isTrue();
    }

    @Test
    @DisplayName("존재하지 않는 상품 번호가 주문에 포함되어 있는 경우 실패한다.")
    void createOrderWithNonexistentProductNumber() throws Exception {
        // given
        Product product1 = createProduct("001", ProductSellingStatus.SELLING);
        Product product2 = createProduct("002", ProductSellingStatus.HOLD);
        productRepository.saveAllAndFlush(List.of(product1, product2));

        // when, then
        assertThatCode(() -> orderCreateService.createOrder(List.of("001", "003", "004")))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("Nonexistent product numbers: [003, 004]");

        List<OrderHistory> orderHistoryList = orderHistoryRepository.findAll();
        assertThat(orderHistoryList.size()).isEqualTo(1);
        assertThat(orderHistoryList.getFirst().isOrderSuccess()).isFalse();
    }

    @Test
    @DisplayName("판매 중지된 상품 번호가 주문에 포함되어 있는 경우 실패한다.")
    void createOrderWithStoppedSellingProductNumber() throws Exception {
        // given
        Product product1 = createProduct("001", ProductSellingStatus.SELLING);
        Product product2 = createProduct("002", ProductSellingStatus.STOP_SELLING);
        Product product3 = createProduct("003", ProductSellingStatus.STOP_SELLING);
        productRepository.saveAllAndFlush(List.of(product1, product2, product3));

        List<String> productNumbers =
            List.of(product1.getProductNumber(), product2.getProductNumber(), product3.getProductNumber());

        // when, then
        assertThatCode(() -> orderCreateService.createOrder(productNumbers))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("Stopped selling product numbers: [002, 003]");

        List<OrderHistory> orderHistoryList = orderHistoryRepository.findAll();
        assertThat(orderHistoryList.size()).isEqualTo(1);
        assertThat(orderHistoryList.getFirst().isOrderSuccess()).isFalse();
    }

    private Product createProduct(String productNumber, ProductSellingStatus status) {
        return new Product(productNumber, ProductType.HANDMADE, status, "TEST_NAME", 4000);
    }
}