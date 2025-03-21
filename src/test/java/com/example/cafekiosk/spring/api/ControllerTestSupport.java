package com.example.cafekiosk.spring.api;

import com.example.cafekiosk.spring.api.controller.OrderCreateController;
import com.example.cafekiosk.spring.api.controller.ProductCreateController;
import com.example.cafekiosk.spring.api.controller.ProductSearchController;
import com.example.cafekiosk.spring.api.service.order.OrderCreateService;
import com.example.cafekiosk.spring.api.service.product.ProductCreateService;
import com.example.cafekiosk.spring.api.service.product.ProductSearchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
    ProductCreateController.class,
    OrderCreateController.class,
    ProductSearchController.class
})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockitoBean
    protected ProductCreateService productCreateService;
    @MockitoBean
    protected OrderCreateService orderCreateService;
    @MockitoBean
    protected ProductSearchService productSearchService;
}