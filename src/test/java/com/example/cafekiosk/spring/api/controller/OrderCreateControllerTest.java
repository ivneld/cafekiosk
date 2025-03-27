package com.example.cafekiosk.spring.api.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.cafekiosk.spring.api.service.order.OrderCreateService;
import com.example.cafekiosk.spring.api.service.order.OrderResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = OrderCreateController.class)
class OrderCreateControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderCreateService orderCreateService;

    @Test
    @DisplayName("상품 번호 리스트로 주문을 생성한다.")
    void createOrder() throws Exception {
        // given
        List<String> productNumbers = List.of("001", "002", "003");

        //stub
        when(orderCreateService.createOrder(productNumbers)).thenReturn(new OrderResult());

        // when, then
        mockMvc.perform(
                   post("/api/v1/orders/new")
                       .content(objectMapper.writeValueAsString(productNumbers))
                       .contentType(MediaType.APPLICATION_JSON)
               )
               .andDo(print())
               .andExpect(status().isOk());
    }
}