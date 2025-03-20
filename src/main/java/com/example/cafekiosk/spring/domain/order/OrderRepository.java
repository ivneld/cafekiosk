package com.example.cafekiosk.spring.domain.order;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findBySerialNumber(String serialNumber);
}