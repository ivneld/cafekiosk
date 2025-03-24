package com.example.cafekiosk.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.cafekiosk.unit.beverage.Americano;
import com.example.cafekiosk.unit.beverage.Latte;
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
}