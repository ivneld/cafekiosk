package com.example.cafekiosk.spring.api.service.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.cafekiosk.spring.domain.order.OrderHistory;
import com.example.cafekiosk.spring.domain.order.OrderRepository;
import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductRepository;
import com.example.cafekiosk.spring.domain.product.ProductSellingStatus;
import com.example.cafekiosk.spring.domain.product.ProductType;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderCreateServiceUnitTest {

    @InjectMocks
    private OrderCreateService orderCreateService;

    @Mock
    private OrderHistoryCreateService orderHistoryCreateService;
    @Mock
    private OrderHistoryUpdateService orderHistoryUpdateService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderRepository orderRepository;

    @Test
    void createOrder() {
        // given
        Product product1 = createProduct("001", ProductSellingStatus.SELLING);
        Product product2 = createProduct("002", ProductSellingStatus.SELLING);
        List<String> productNumbers = List.of(product1.getProductNumber(), product2.getProductNumber());

        // stub
        when(productRepository.findAllByProductNumberIn(productNumbers)).thenReturn(List.of(product1, product2));
        when(orderHistoryCreateService.create(any(), any())).thenReturn(OrderHistory.create(any(), productNumbers));

        // when
        OrderResult orderResult = orderCreateService.createOrder(productNumbers);

        // then
        assertThat(orderResult.getTotalPrice()).isEqualTo(product1.getPrice() + product2.getPrice());
        assertThat(orderResult.getProductNumbers()).isEqualTo(productNumbers);
    }

    @Test
    void createOrder_productNotFound() {
        // given
        Product product1 = createProduct("001", ProductSellingStatus.SELLING);
        Product product2 = createProduct("002", ProductSellingStatus.SELLING);
        Product product3 = createProduct("003", ProductSellingStatus.SELLING);
        List<String> productNumbers =
            List.of(product1.getProductNumber(), product2.getProductNumber(), product3.getProductNumber());

        // stub
        when(productRepository.findAllByProductNumberIn(productNumbers)).thenReturn(List.of(product1, product2));

        // when, then
        assertThatCode(() -> orderCreateService.createOrder(productNumbers))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid product numbers: " + List.of(product3.getProductNumber()));
    }

    @Test
    void createOrder_stoppedSellingProductNumbers() {
        // given
        Product product1 = createProduct("001", ProductSellingStatus.SELLING);
        Product product2 = createProduct("002", ProductSellingStatus.STOP_SELLING);
        Product product3 = createProduct("003", ProductSellingStatus.STOP_SELLING);
        List<String> productNumbers =
            List.of(product1.getProductNumber(), product2.getProductNumber(), product3.getProductNumber());

        // stub
        when(productRepository.findAllByProductNumberIn(productNumbers)).thenReturn(List.of(product1, product2, product3));

        // when, then
        assertThatCode(() -> orderCreateService.createOrder(productNumbers))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("Product status is STOP_SELLING. Product numbers: " + List.of(product2.getProductNumber(), product3.getProductNumber()));
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