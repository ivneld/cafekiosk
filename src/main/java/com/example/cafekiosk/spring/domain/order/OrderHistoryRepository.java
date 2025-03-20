package com.example.cafekiosk.spring.domain.order;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
    Optional<OrderHistory> findByOrderSerialNumber(String orderSerialNumber);

    boolean existsByOrderSerialNumber(String orderSerialNumber);
}