package com.example.cafekiosk.spring.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * SELECT * FROM Product WHERE selling_type in ('');
     * @param sellingTypes
     * @return
     */
    List<Product> findAllBySellingStatusIn(List<ProductSellingStatus> sellingTypes);
}
