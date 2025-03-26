package com.example.cafekiosk.spring.domain.order;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
    boolean existsByOrderSerialNumber(String orderSerialNumber);
    Optional<OrderHistory> findByOrderSerialNumber(String orderSerialNumber);
}