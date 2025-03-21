package com.example.cafekiosk.spring.api.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.cafekiosk.spring.api.ControllerTestSupport;
import com.example.cafekiosk.spring.api.service.order.OrderResult;
import com.example.cafekiosk.spring.domain.order.Order;
import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductSellingStatus;
import com.example.cafekiosk.spring.domain.product.ProductType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class OrderCreateControllerTest extends ControllerTestSupport {

    @Test
    @DisplayName("주문 번호 리스트를 통해 주문을 생성하고 주문 정보를 반환한다.")
    void createOrder() throws Exception {
        // given
        Product product1 = createProduct("001");
        Product product2 = createProduct("002");
        List<String> productNumbers = List.of(product1.getProductNumber(), product2.getProductNumber());

        Order order = Order.create(List.of(product1, product2));
        OrderResult orderResult = OrderResult.of(order);

        // stubbing
        Mockito.when(orderCreateService.createOrder(Mockito.anyList())).thenReturn(orderResult);

        // when, then
        mockMvc.perform(
                   MockMvcRequestBuilders.post("/api/v1/orders/new")
                                         .contentType(MediaType.APPLICATION_JSON)
                                         .content(objectMapper.writeValueAsString(productNumbers)))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(content().json(objectMapper.writeValueAsString(orderResult)));
    }

    private Product createProduct(String productNumber) {
        return new Product(productNumber, ProductType.HANDMAND, ProductSellingStatus.SELLING, "Test", 4000);
    }

}