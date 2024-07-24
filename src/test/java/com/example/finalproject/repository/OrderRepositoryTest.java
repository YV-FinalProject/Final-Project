package com.example.finalproject.repository;

import com.example.finalproject.entity.Order;
import com.example.finalproject.entity.enums.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;

import java.sql.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    private Timestamp startDate;

    @BeforeEach
    public void setUp() {
        startDate = Timestamp.valueOf("2024-06-20 00:00:00");
        orderRepository.deleteAll();

        Order order1 = new Order();
        order1.setOrderId(1L);
        order1.setStatus(Status.CREATED);
        order1.setCreatedAt(Timestamp.valueOf("2024-06-21 00:00:00"));
        order1.setUpdatedAt(order1.getCreatedAt());
        orderRepository.save(order1);

        Order order2 = new Order();
        order2.setOrderId(2L);
        order2.setStatus(Status.PENDING_PAYMENT);
        order2.setCreatedAt(Timestamp.valueOf("2024-06-21 00:00:00"));
        order2.setUpdatedAt(order2.getCreatedAt());
        orderRepository.save(order2);

        Order order3 = new Order();
        order3.setOrderId(3L);
        order3.setStatus(Status.PAID);
        order3.setCreatedAt(Timestamp.valueOf("2024-06-21 00:00:00"));
        order3.setUpdatedAt(order3.getCreatedAt());
        orderRepository.save(order3);

        Order order4 = new Order();
        order4.setOrderId(4L);
        order4.setStatus(Status.ON_THE_WAY);
        order4.setCreatedAt(Timestamp.valueOf("2024-06-21 00:00:00"));
        order4.setUpdatedAt(order4.getCreatedAt());
        orderRepository.save(order4);

        Order order5 = new Order();
        order5.setOrderId(5L);
        order5.setStatus(Status.DELIVERED);
        order5.setCreatedAt(Timestamp.valueOf("2024-06-21 00:00:00"));
        order5.setUpdatedAt(order5.getCreatedAt());
        orderRepository.save(order5);

        Order order6 = new Order();
        order6.setOrderId(6L);
        order6.setStatus(Status.CANCELED);
        order6.setCreatedAt(Timestamp.valueOf("2024-06-21 00:00:00"));
        order6.setUpdatedAt(order6.getCreatedAt());
        orderRepository.save(order6);
    }

    @Test
    void updateOrderStatuses() {
        List<Status> statusesToUpdate = Arrays.asList(Status.CREATED, Status.PENDING_PAYMENT, Status.PAID, Status.ON_THE_WAY);

        Integer updatedOrders = orderRepository.updateOrderStatuses(startDate, statusesToUpdate);

        assertThat(updatedOrders).isEqualTo(4);
    }
}