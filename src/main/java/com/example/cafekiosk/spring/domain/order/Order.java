package com.example.cafekiosk.spring.domain.order;

import com.example.cafekiosk.spring.domain.SerialNumberUtils;
import com.example.cafekiosk.spring.domain.orderproduct.OrderProduct;
import com.example.cafekiosk.spring.domain.product.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String serialNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(nullable = false)
    private int totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    public Order(String serialNumber, OrderStatus orderStatus, int totalPrice, List<OrderProduct> orderProducts) {
        this.serialNumber = serialNumber;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.orderProducts = orderProducts;
    }

    private Order(List<Product> products) {
        this.serialNumber = SerialNumberUtils.generate();
        this.orderStatus = OrderStatus.INIT;
        this.totalPrice = calculateTotalPrice(products);
        this.orderProducts = products.stream()
                                     .map(product -> new OrderProduct(this, product))
                                     .collect(Collectors.toList());
    }

    public static Order create(List<Product> products) {
        return new Order(products);
    }

    private int calculateTotalPrice(List<Product> products) {
        return products.stream()
                       .mapToInt(Product::getPrice)
                       .sum();
    }
}