package com.iqbal.orderservice.repository;

import com.iqbal.orderservice.entity.Order;
import com.iqbal.orderservice.entity.OrderLineItems;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class OrderRepositoryTest {

    private final OrderRepository repository;

    @Autowired
    public OrderRepositoryTest(OrderRepository repository) {
        this.repository = repository;
    }

    @Test
    void orderRepository_SaveAll_ReturnOrder() {

        List<OrderLineItems> itemsList = new ArrayList<>();

        OrderLineItems orderLineItems = OrderLineItems.builder()
                .skuCode("Iphone 15")
                .price(BigDecimal.valueOf(2000))
                .quantity(1)
                .build();

        itemsList.add(orderLineItems);

        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItemsList(itemsList)
                .build();

        Order saved = repository.save(order);

        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.getId()).isNotNull();
        Assertions.assertThat(saved.getId()).isPositive();
    }
}