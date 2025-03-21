package com.example.cafekiosk.spring.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.cafekiosk.spring.api.ControllerTestSupport;
import com.example.cafekiosk.spring.api.service.product.ProductCreateRequest;
import com.example.cafekiosk.spring.domain.product.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

class ProductCreateControllerTest extends ControllerTestSupport {

    @Test
    @DisplayName("신규 상품을 등록한다.")
    void createProduct() throws Exception {
        // given
        var request = ProductCreateRequest.builder()
                                          .type(ProductType.HANDMAND)
                                          .name("Americano")
                                          .price(4000)
                                          .build();

        // when, then
        mockMvc.perform(
                   post("/api/v1/product")
                                         .content(objectMapper.writeValueAsString(request))
                                         .contentType(MediaType.APPLICATION_JSON)
               )
               .andDo(print())
               .andExpect(status().isOk());
    }

    @Test
    @DisplayName("신규 상품을 등록할 때 상품 타입은 필수값이다.")
    void createProductWithoutSellingStatus() throws Exception {
        // given
        var request = ProductCreateRequest.builder()
                                          .name("Americano")
                                          .price(4000)
                                          .build();

        // when, then
        MvcResult mvcResult = mockMvc.perform(
                                         post("/api/v1/product")
                                             .content(objectMapper.writeValueAsString(request))
                                             .contentType(MediaType.APPLICATION_JSON)
                                     )
                                     .andDo(print())
                                     .andExpect(status().isBadRequest())
                                     .andExpect(content().string("상품 타입은 필수입니다."))
                                     .andReturn();
    }
}