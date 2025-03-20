package com.example.cafekiosk.spring.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.cafekiosk.spring.domain.product.ProductRepository;
import com.example.cafekiosk.spring.domain.product.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class ProductCreateServiceTest {

    @Autowired
    private ProductCreateService productCreateService;
    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("새로운 상품의 정보로 상품을 생성하고 저장한다.")
    void create() {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                                                             .type(ProductType.HANDMAND)
                                                             .name("Americano")
                                                             .price(3500)
                                                             .build();

        // when
        ProductDetailInfo productDetailInfo = productCreateService.create(request);

        // then
        assertThat(productRepository.findByProductNumber(productDetailInfo.getProductNumber())).isNotEmpty();
    }
}