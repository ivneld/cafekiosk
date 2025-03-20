package com.example.cafekiosk.spring.api.controller;

import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductSellingStatus;
import com.example.cafekiosk.spring.domain.product.ProductType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("주문 번호 리스트를 통해 주문을 생성하고 주문 정보를 반환한다.")
    void createOrder() throws Exception {
        // given
        Product product1 = createProduct("001", ProductSellingStatus.SELLING);
        Product product2 = createProduct("002", ProductSellingStatus.SELLING);
        List<String> productNumbers = List.of(product1.getProductNumber(), product2.getProductNumber());

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders/new")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(objectMapper.writeValueAsString(productNumbers))
        ).andExpect(MockMvcResultMatchers.status().isOk());
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