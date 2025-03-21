package com.example.cafekiosk.unit;

import com.example.cafekiosk.unit.beverage.Beverage;
import com.example.cafekiosk.unit.order.Order;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class CafeKiosk {

    private static final LocalTime SHOP_OPEN_TIME = LocalTime.of(10, 0);
    private static final LocalTime SHOP_CLOSE_TIME = LocalTime.of(22, 0);

    private final List<Beverage> beverages = new ArrayList<>();

    public void add(Beverage beverage) {
        beverages.add(beverage);
    }

    public void add(Beverage beverage, int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("음료는 1잔 이상 주문할 수 있습니다.");
        }

        for (int i = 0; i < count; i++) {
            beverages.add(beverage);
        }
    }

    public void remove(Beverage beverage) {
        beverages.remove(beverage);
    }

    public void clear() {
        beverages.clear();
    }

//    public int calculateTotalPrice() {
//        int totalPrice = 0;
//        for (Beverage beverage : beverages) {
//            totalPrice += beverage.getPrice();
//        }
//        return totalPrice;
//    }

    // NOTE : TDD
    // NOTE : RED
    // public int calculateTotalPrice() {
    //     return 0;
    // }

    // NOTE : GREEN
    // public int calculateTotalPrice() {
    //     int totalPrice = 0;
    //     for (Beverage beverage : beverages) {
    //         totalPrice += beverage.getPrice();
    //     }
    //     return totalPrice;
    // }

    // NOTE : REFACTOR
    public int calculateTotalPrice() {
        return beverages.stream().mapToInt(Beverage::getPrice).sum();
    }


    public Order createOrder() {
        LocalTime currentTime = LocalDateTime.now().toLocalTime();

        if (this.beverages.isEmpty()) {
            throw new IllegalArgumentException("주문할 음료를 추가하세요.");
        }

        if (currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)) {
            throw new IllegalArgumentException("주문 시간이 아닙니다.");
        }

        return new Order(LocalDateTime.now(), beverages);
    }

    public Order createOrder(LocalDateTime orderTime) {
        LocalTime currentTime = orderTime.toLocalTime();
        if (currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)) {
            throw new IllegalArgumentException("주문 시간이 아닙니다.");
        }

        return new Order(LocalDateTime.now(), beverages);
    }
}