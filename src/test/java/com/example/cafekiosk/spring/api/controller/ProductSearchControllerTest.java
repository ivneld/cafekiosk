package com.example.cafekiosk.spring.api.controller;

import static org.mockito.Mockito.when;

import com.example.cafekiosk.spring.api.service.ProductDetailInfo;
import com.example.cafekiosk.spring.api.service.ProductSearchService;
import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductSellingStatus;
import com.example.cafekiosk.spring.domain.product.ProductType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = ProductSearchController.class)
class ProductSearchControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

//    @MockBean -> deprecated
    @MockitoBean
    private ProductSearchService productSearchService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("판매 중인 상품의 상세 정보 리스트를 조회한다.")
    void getSellingProducts() throws Exception {
        // given
        List<ProductDetailInfo> expectedResponse = List.of(
            ProductDetailInfo.of(createProduct("001", ProductSellingStatus.SELLING)),
            ProductDetailInfo.of(createProduct("002", ProductSellingStatus.HOLD)),
            ProductDetailInfo.of(createProduct("003", ProductSellingStatus.STOP_SELLING))
        );

        // stubbing
        when(productSearchService.getSellingProducts()).thenReturn(expectedResponse);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/selling"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedResponse)));
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