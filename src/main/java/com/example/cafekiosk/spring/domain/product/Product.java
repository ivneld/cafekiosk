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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String productNumber;

    @Enumerated(EnumType.STRING)
    private ProductType type;

    @Enumerated(EnumType.STRING)
    private ProductSellingStatus sellingStatus;

    private String name;
    private int price;

    @Builder
    private Product(
        Long id,
        String productNumber,
        ProductType type,
        ProductSellingStatus sellingStatus,
        String name,
        int price) {
        this.id = id;
        this.productNumber = productNumber;
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public Product(String productNumber, ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        this.productNumber = productNumber;
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    private Product(ProductType type, String name, int price) {
        this.productNumber = UUID.randomUUID().toString().substring(0, 8);
        this.type = type;
        this.sellingStatus = ProductSellingStatus.HOLD;
        this.name = name;
        this.price = price;
    }

    public static Product create(ProductType type, String name, int price) {
        return new Product(type, name, price);
    }

    public void startSelling() {
        if (ProductSellingStatus.STOP_SELLING.equals(this.sellingStatus)) {
            throw new IllegalStateException("해당 상품은 판매 중지된 상품입니다.");
        }
        this.sellingStatus = ProductSellingStatus.SELLING;
    }

    public void stopSelling() {
        this.sellingStatus = ProductSellingStatus.STOP_SELLING;
    }
}