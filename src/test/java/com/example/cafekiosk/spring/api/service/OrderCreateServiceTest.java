package com.example.cafekiosk.spring.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductRepository;
import com.example.cafekiosk.spring.domain.product.ProductSellingStatus;
import com.example.cafekiosk.spring.domain.product.ProductType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class OrderCreateServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderCreateService orderCreateService;

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
        assertThat(orderResult.getProductNumbers().size()).isEqualTo(productNumbers.size());
        assertThat(orderResult.getProductNumbers())
            .contains(product1.getProductNumber(), product2.getProductNumber(), product3.getProductNumber());
        assertThat(orderResult.getTotalPrice())
            .isEqualTo(product1.getPrice() + product2.getPrice() + product3.getPrice());
    }

    @Test
    @DisplayName("존재하지 않는 상품번호가 주문에 포함되어 있는 경우 실패한다.")
    void createOrderWithInvalidProductNumber() {
        // given
        Product product1 = createProduct("001", ProductSellingStatus.SELLING);
        Product product2 = createProduct("002", ProductSellingStatus.HOLD);
        productRepository.saveAll(List.of(product1, product2));

        // when, then
        assertThatCode(() -> orderCreateService.createOrder(List.of("003", "004")))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid product numbers: [003, 004]");
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