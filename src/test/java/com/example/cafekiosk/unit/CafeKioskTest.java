package com.example.cafekiosk.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.cafekiosk.unit.beverage.Americano;
import com.example.cafekiosk.unit.beverage.Latte;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class CafeKioskTest {

    @Test
    void add() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        assertThat(cafeKiosk.getBeverages()).hasSize(1);
        assertThat(cafeKiosk.getBeverages().getFirst().getName()).isEqualTo("Americano");
        assertThat(cafeKiosk.getBeverages().getLast()).isNotEqualTo(new Americano());
        assertThat(cafeKiosk.getBeverages().getLast()).usingRecursiveComparison().isEqualTo(new Americano());
    }

    @Test
    void addSeveralBeverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano(), 2);

        assertThat(cafeKiosk.getBeverages()).hasSize(2);
    }

    @Test
    void addZeroBeverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();

        assertThatThrownBy(() -> cafeKiosk.add(new Americano(), 0))
            .isInstanceOf(RuntimeException.class)
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("음료는 1잔 이상 주문할 수 있습니다.");
    }

    @Test
    void remove() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);

        cafeKiosk.remove(americano);

        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @Test
    void clear() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());
        cafeKiosk.add(new Latte());

        cafeKiosk.clear();

        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @Test
    void createOrderWithEmptyBeverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();

        LocalDateTime orderDateTime = LocalDateTime.of(2025, 3, 24, 11, 0);

        assertThatThrownBy(() -> cafeKiosk.createOrder(orderDateTime))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("주문할 음료를 추가하세요.");
    }

    @Test
    void createOrder() {
        CafeKiosk cafeKiosk = new CafeKiosk();

        LocalDateTime orderDateTime = LocalDateTime.of(2025, 3, 24, 11, 0);

        Americano americano = new Americano();
        cafeKiosk.add(americano);

        assertThatCode(() -> cafeKiosk.createOrder(orderDateTime))
            .doesNotThrowAnyException();
    }

    @Test
    void createOrderWithOutOfRangeOrderDateTime() {
        CafeKiosk cafeKiosk = new CafeKiosk();

        LocalDateTime orderDateTime = LocalDateTime.of(2025, 3, 24, 9, 0);

        Americano americano = new Americano();
        cafeKiosk.add(americano);

        assertThatCode(() -> cafeKiosk.createOrder(orderDateTime))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("주문 시간이 아닙니다.");
    }

    @Test
    void calculateTotalPrice() {
        Americano americano = new Americano();
        Latte latte = new Latte();

        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(americano, 2);
        cafeKiosk.add(latte);

        int expectedPrice = americano.getPrice() * 2 + latte.getPrice();

        assertThat(cafeKiosk.calculateTotalPrice()).isEqualTo(expectedPrice);
    }
}