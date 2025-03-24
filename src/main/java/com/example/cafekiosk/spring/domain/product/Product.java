package com.example.cafekiosk.spring.domain.product;

import com.example.cafekiosk.spring.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String productNumber;

    @Enumerated(EnumType.STRING)
    private ProductType type;

    @Enumerated(EnumType.STRING)
    private ProductSellingStatus sellingStatus;

    private String name;
    private int price;

    public Product(String productNumber, ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        this.productNumber = productNumber;
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public static Product create(ProductType productType, String name, int price) {
        return new Product(
            UUID.randomUUID().toString().substring(0, 8),
            productType,
            ProductSellingStatus.HOLD,
            name,
            price
        );
    }

    public static Product create(String productNumber, ProductType productType, String name, int price) {
        return new Product(
            productNumber,
            productType,
            ProductSellingStatus.HOLD,
            name,
            price
        );
    }

    public void startSelling() {
        if (this.sellingStatus == ProductSellingStatus.STOP_SELLING) {
            throw new IllegalStateException("판매 중지된 상품은 판매 재개할 수 없습니다.");
        }
        this.sellingStatus = ProductSellingStatus.SELLING;
    }

    public void stopSelling() {
        this.sellingStatus = ProductSellingStatus.STOP_SELLING;
    }

    public void hold() {
        this.sellingStatus = ProductSellingStatus.HOLD;
    }
}