package com.iqbal.productservice.repository;

import com.iqbal.productservice.entity.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ProductRepositoryTest {

    private final ProductRepository repository;

    @Autowired
    ProductRepositoryTest(ProductRepository repository) {
        this.repository = repository;
    }

    @Test
    void ProductRepository_SaveAll_ReturnProduct() {
        Product product = Product.builder()
                .name("Iphone 15")
                .description("iphone 15")
                .price(BigDecimal.valueOf(2000))
                .build();

        Product save = repository.saveAndFlush(product);

        Assertions.assertThat(save).isNotNull();
        Assertions.assertThat(save.getId()).isGreaterThan("");
    }

    @Test
    void ProductRepository_GetAll_ReturnMoreOneThanProduct() {
        Product product = Product.builder()
                .name("Iphone 14")
                .description("iphone 14")
                .price(BigDecimal.valueOf(1000))
                .build();

        Product product2 = Product.builder()
                .name("Iphone 15")
                .description("iphone 15")
                .price(BigDecimal.valueOf(2000))
                .build();

        repository.save(product);
        repository.save(product2);

        List<Product> all = repository.findAll();

        Assertions.assertThat(all).hasSize(2).isNotNull();
    }

    @Test
    void findByName() {

        Product product = Product.builder()
                .name("Iphone 14")
                .description("iphone 14")
                .price(BigDecimal.valueOf(1000))
                .build();

        repository.save(product);

        Product getName = repository.findByName(product.getName()).orElseThrow();

        Assertions.assertThat(getName).isNotNull();
    }

    @Test
    void findById() {

        Product product = Product.builder()
                .name("Iphone 14")
                .description("iphone 14")
                .price(BigDecimal.valueOf(1000))
                .build();

        repository.save(product);

        Product getId = repository.findById(product.getId()).orElseThrow();

        Assertions.assertThat(getId).isNotNull();
    }
}