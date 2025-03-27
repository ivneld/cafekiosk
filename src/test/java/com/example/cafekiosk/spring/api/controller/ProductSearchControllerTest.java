package com.example.cafekiosk.spring.api.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.cafekiosk.spring.api.service.product.ProductDetailInfo;
import com.example.cafekiosk.spring.api.service.product.ProductSearchService;
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
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ProductSearchController.class)
class ProductSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductSearchService productSearchService;

    @Test
    @DisplayName("현재 판매 중인 상품 리스트를 조회한다.")
    void findSellingStatus() throws Exception {
        // given
        Product americano = new Product("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "Americano", 3000);
        Product latte = new Product("002", ProductType.HANDMADE, ProductSellingStatus.SELLING, "Latte", 4000);

        // stub
        when(productSearchService.findSellingProducts()).thenReturn(List.of(ProductDetailInfo.of(americano), ProductDetailInfo.of(latte)));

        // when, then
        mockMvc.perform(
                   get("/api/v1/products/selling")
                       .content(objectMapper.writeValueAsString(americano))
                       .contentType(MediaType.APPLICATION_JSON)
               )
               .andDo(print())
               .andExpect(status().isOk());
    }
}