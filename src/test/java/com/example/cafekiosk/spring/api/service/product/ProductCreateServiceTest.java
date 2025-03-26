package com.example.cafekiosk.spring.api.service.product;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.cafekiosk.spring.domain.product.ProductSellingStatus;
import com.example.cafekiosk.spring.domain.product.ProductType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ProductCreateServiceTest {

    @Autowired
    private ProductCreateService productCreateService;

    @Test
    void create() {
        // given
        var americano = ProductCreateRequest.builder()
                                            .type(ProductType.HANDMADE)
                                            .name("Americano")
                                            .price(3000)
                                            .build();

        // when
        ProductDetailInfo productDetailInfo = productCreateService.create(americano);

        // then
        assertThat(productDetailInfo.getProductNumber()).isNotEmpty();
        assertThat(productDetailInfo.getSellingStatus()).isEqualTo(ProductSellingStatus.HOLD);
    }
}