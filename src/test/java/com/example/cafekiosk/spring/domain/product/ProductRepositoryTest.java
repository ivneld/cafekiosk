package com.example.cafekiosk.spring.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.List;
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

    @Test
    @DisplayName("SELLING, HOLD 인 상태의 상품들을 조회한다.")
    void findAllBySellingStatusIn() throws Exception {
        // given
        Product testProduct1 = createTestProduct("001", ProductSellingStatus.SELLING);
        Product testProduct2 = createTestProduct("002", ProductSellingStatus.HOLD);
        Product testProduct3 = createTestProduct("003", ProductSellingStatus.STOP_SELLING);

        productRepository.saveAll(List.of(testProduct1, testProduct2, testProduct3));

        // when
        List<Product> findProducts = productRepository.findAllBySellingStatusIn(List.of(ProductSellingStatus.SELLING, ProductSellingStatus.HOLD));

        // then
        assertThat(findProducts).hasSize(2)
                                .extracting("productNumber", "sellingStatus")
                                .containsExactlyInAnyOrder(
                                    Tuple.tuple(testProduct1.getProductNumber(), ProductSellingStatus.SELLING),
                                    Tuple.tuple(testProduct2.getProductNumber(), ProductSellingStatus.HOLD)
                                );
    }

    @Test
    void saveWithDuplicateProductNumber() {
        // given
        List<Product> testProducts = List.of(
            createTestProduct("001", ProductSellingStatus.SELLING),
            createTestProduct("001", ProductSellingStatus.SELLING)
        );

        // when, then
        assertThatCode(() -> productRepository.saveAllAndFlush(testProducts))
            .isExactlyInstanceOf(DataIntegrityViolationException.class);
    }

    private Product createTestProduct(String productNumber, ProductSellingStatus sellingStatus) {
        return new Product(productNumber, ProductType.HANDMADE, sellingStatus, "TEST_PRODUCT", 3000);
    }
}