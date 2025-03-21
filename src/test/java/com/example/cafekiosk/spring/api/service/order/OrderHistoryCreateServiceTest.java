package com.example.cafekiosk.spring.api.service.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.example.cafekiosk.spring.domain.order.OrderHistoryRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@Transactional
@SpringBootTest
class OrderHistoryCreateServiceTest {

    @Autowired
    private OrderHistoryCreateService service;
    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @Container
    static PostgreSQLContainer postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);
    }

    @Test
    @DisplayName("주문 고유 번호와 상품 번호 리스트로 주문 이력을 저장한다.")
    void create() throws Exception {
        // given
        List<String> productNumbers = List.of("001", "002", "003");
        String orderSerialNumber = UUID.randomUUID().toString().substring(0, 8);

        // when
        service.create(orderSerialNumber, productNumbers);

        // then
        assertThat(orderHistoryRepository.findByOrderSerialNumber(orderSerialNumber)).isPresent();
    }

    @Test
    @DisplayName("중복된 주문 번호에 대한 이력 저장 요청은 존재할 수 없다.")
    void createWithDuplicatedOrderSerialNumber() throws Exception {
        // given
        List<String> firstProductNumbers = List.of("001", "002");
        List<String> secondProductNumbers = List.of("003", "004");

        String orderSerialNumber = UUID.randomUUID().toString().substring(0, 8);

        service.create(orderSerialNumber, firstProductNumbers);

        // when, then
        assertThatCode(() -> service.create(orderSerialNumber, secondProductNumbers))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("Order serial number " + orderSerialNumber + " already exists");
    }
}