package com.iqbal.inventoryservice.repository;

import com.iqbal.inventoryservice.entity.Inventory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class InventoryRepositoryTest {

    private final InventoryRepository repository;

    @Autowired
    public InventoryRepositoryTest(InventoryRepository repository) {
        this.repository = repository;
    }

    @Test
    void findBySkuCode() {
        Inventory inventory = Inventory.builder()
                .id(1L)
                .skuCode("Iphone 15")
                .quantity(12)
                .build();
        repository.save(inventory);

        Inventory getInventory = repository.findBySkuCode(inventory.getSkuCode()).orElseThrow();

        Assertions.assertThat(getInventory).isNotNull();
    }
}