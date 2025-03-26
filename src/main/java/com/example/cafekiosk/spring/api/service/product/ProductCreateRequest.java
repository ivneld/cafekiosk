package com.example.cafekiosk.spring.api.service.product;

import com.example.cafekiosk.spring.domain.product.ProductType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductCreateRequest {

    @NotNull(message = "상품 타입은 필수입니다.")
    private ProductType type;
    @NotNull(message = "상품 이름은 필수입니다.")
    private String name;
    @Positive(message = "잘못된 상품 가격입니다.")
    private int price;
}
