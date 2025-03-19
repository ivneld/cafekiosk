package com.example.cafekiosk.spring.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductRepository;
import com.example.cafekiosk.spring.domain.product.ProductSellingStatus;
import com.example.cafekiosk.spring.domain.product.ProductType;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("SELLING, HOLD 인 상태의 상품들을 조회한다.")
    void findAllBySellingStatusIn() throws Exception {
        // given
        Product americano = createProduct("001", "Americano", ProductSellingStatus.SELLING);
        Product latte = createProduct("002", "Latte", ProductSellingStatus.HOLD);
        Product pineapple = createProduct("003", "Pineapple", ProductSellingStatus.STOP_SELLING);

        productRepository.saveAll(List.of(americano, latte, pineapple));

        // when
        List<Product> findProducts = productRepository.findAllBySellingStatusIn(List.of(ProductSellingStatus.SELLING, ProductSellingStatus.HOLD));

        // then
        assertThat(findProducts).hasSize(2)
                                .extracting("productNumber", "name", "sellingStatus")
                                .containsExactlyInAnyOrder(
                                    Tuple.tuple(americano.getProductNumber(), americano.getName(), americano.getSellingStatus()),
                                    Tuple.tuple(latte.getProductNumber(), latte.getName(), latte.getSellingStatus())
                                );
    }

    @Test
    void findByProductNumber() throws Exception {
        // given
        Product americano = createProduct("001", "Americano", ProductSellingStatus.SELLING);
        Product latte = createProduct("002", "Latte", ProductSellingStatus.HOLD);
        productRepository.saveAll(List.of(americano, latte));

        entityManager.flush();
        entityManager.clear();

        // when
        Optional<Product> optionalProduct = productRepository.findById(americano.getId());

        // then
        assertThat(optionalProduct).isPresent();
        Product findProduct = optionalProduct.get();
        assertThat(findProduct).usingRecursiveComparison().isEqualTo(americano);
    }

    @Test
    void saveWithDuplicateProductNumber() {
        Product product1 = createProduct("001", "Product1", ProductSellingStatus.SELLING);
        Product product2 = createProduct("001", "Product2", ProductSellingStatus.SELLING);

        productRepository.save(product1);

        assertThatThrownBy(() -> productRepository.save(product2)).isExactlyInstanceOf(DataIntegrityViolationException.class);
    }

    private Product createProduct(String productNumber, String name, ProductSellingStatus status) {
        return Product.builder()
                      .productNumber(productNumber)
                      .type(ProductType.HANDMAND)
                      .name(name)
                      .sellingStatus(status)
                      .price(4000)
                      .build();
    }

    @Test
    @DisplayName("상품번호 리스트로 통해 상품을 조회한다.")
    void findAllByProductNumberIn() {
        // given
        Product americano = createProduct("001", "Americano", ProductSellingStatus.SELLING);
        Product latte = createProduct("002", "Latte", ProductSellingStatus.HOLD);
        Product pineapple = createProduct("003", "Pineapple", ProductSellingStatus.STOP_SELLING);

        productRepository.saveAll(List.of(americano, latte, pineapple));

        // when
        List<Product> products = productRepository.findAllByProductNumberIn(List.of("001", "002", "003", "004"));

        // then
        assertThat(products).hasSize(3);
        assertThat(products).contains(americano, latte, pineapple);
    }
}