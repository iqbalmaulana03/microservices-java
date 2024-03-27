package com.iqbal.inventoryservice.repository;

import com.iqbal.inventoryservice.entity.Inventory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

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
        List<String> skuCodes = Arrays.asList("SKU001", "SKU002");

        Inventory inventory = Inventory.builder()
                .id(1L)
                .skuCode("SKU001")
                .quantity(1)
                .build();

        Inventory inventory2 = Inventory.builder()
                .id(1L)
                .skuCode("SKU002")
                .quantity(1)
                .build();
        repository.save(inventory);
        repository.save(inventory2);

        List<Inventory> getInventory = repository.findBySkuCodeIn(skuCodes);

        Assertions.assertThat(getInventory).isNotNull();
    }
}