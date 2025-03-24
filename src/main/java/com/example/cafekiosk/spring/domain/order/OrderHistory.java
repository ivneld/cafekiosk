package com.example.cafekiosk.spring.domain.order;

import com.example.cafekiosk.spring.domain.BaseEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String orderSerialNumber;
    @Column(nullable = false)
    private String productNumbers;
    @Column(nullable = false)
    private boolean isOrderSuccess;

    public OrderHistory(String orderSerialNumber, String productNumbers, boolean isOrderSuccess) {
        this.orderSerialNumber = orderSerialNumber;
        this.productNumbers = productNumbers;
        this.isOrderSuccess = isOrderSuccess;
    }

    private OrderHistory(String orderSerialNumber, List<String> productNumbers) {
        this.orderSerialNumber = orderSerialNumber;
        this.productNumbers = StringListJsonConverter.toJson(productNumbers);
        this.isOrderSuccess = false;
    }

    public static OrderHistory create(String orderSerialNumber, List<String> productNumbers) {
        return new OrderHistory(orderSerialNumber, productNumbers);
    }

    public void onOrderSuccess() {
        this.isOrderSuccess = true;
    }
}