package com.example.cafekiosk.spring.domain.product;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * SELECT * FROM Product WHERE selling_type in ('');
     * @param sellingTypes
     * @return
     */
    List<Product> findAllBySellingStatusIn(List<ProductSellingStatus> sellingTypes);

    Optional<Product> findByProductNumber(String productNumber);

    List<Product> findAllByProductNumberIn(List<String> productNumbers);
}