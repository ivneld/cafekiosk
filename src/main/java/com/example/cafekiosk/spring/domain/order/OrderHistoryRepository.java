package com.example.cafekiosk.spring.domain.order;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
    List<OrderHistory> findByOrderSerialNumber(String orderSerialNumber);

    boolean existsByOrderSerialNumber(String orderSerialNumber);
}