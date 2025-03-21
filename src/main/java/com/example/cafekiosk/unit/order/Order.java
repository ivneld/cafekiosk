package com.example.cafekiosk.unit.order;

import com.example.cafekiosk.unit.beverage.Beverage;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class Order {

    private final LocalDateTime orderDateTime;
    private final List<Beverage> beverages;

    public Order(LocalDateTime orderDateTime, List<Beverage> beverages) {
        this.orderDateTime = orderDateTime;
        this.beverages = beverages;
    }
}