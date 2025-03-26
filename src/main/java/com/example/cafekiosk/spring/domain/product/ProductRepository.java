package com.example.cafekiosk.spring.domain.product;

import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * SELECT * FROM Product WHERE selling_type in ('');
     */
    List<Product> findAllBySellingStatusIn(List<ProductSellingStatus> sellingTypes);
    List<Product> findAllByProductNumberIn(List<String> productNumbers);
}