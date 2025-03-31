package com.example.cafekiosk.spring.api.service.order;

import static org.assertj.core.api.Assertions.assertThatCode;

import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductSellingStatus;
import com.example.cafekiosk.spring.domain.product.ProductType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductCollectionValidatorTest {

    @Test
    @DisplayName("상품 번호 리스트로 존재하지 않는 상품 번호가 있는지 검증한다.")
    void validateExistence() throws Exception {
        // given
        Product product1 = createProduct("001", ProductSellingStatus.SELLING);
        Product product2 = createProduct("002", ProductSellingStatus.HOLD);
        Product product3 = createProduct("003", ProductSellingStatus.STOP_SELLING);
        List<Product> products = List.of(product1, product2, product3);

        List<String> productNumbers = List.of(product1.getProductNumber(), product2.getProductNumber(), "010");

        // when
        ProductCollectionValidator validator = new ProductCollectionValidator(products);

        // then
        assertThatCode(() -> validator.validateExistence(productNumbers))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("Nonexistent product numbers: [010]");
    }

    @Test
    @DisplayName("상품 번호 리스트로 판매 중지된 상품가 있는지 검증한다.")
    void validateStoppedSelling() throws Exception {
        // given
        Product product1 = createProduct("001", ProductSellingStatus.SELLING);
        Product product2 = createProduct("002", ProductSellingStatus.HOLD);
        Product product3 = createProduct("003", ProductSellingStatus.STOP_SELLING);
        List<Product> products = List.of(product1, product2, product3);

        // when
        ProductCollectionValidator validator = new ProductCollectionValidator(products);

        // then
        assertThatCode(validator::validateStoppedSelling)
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("Stopped selling product numbers: [003]");
    }

    private Product createProduct(String productNumber, ProductSellingStatus status) {
        return new Product(productNumber, ProductType.HANDMADE, status, "TEST_NAME", 4000);
    }
}