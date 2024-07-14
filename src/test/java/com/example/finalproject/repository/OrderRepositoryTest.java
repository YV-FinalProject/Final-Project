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
        order1.setStatus(Status.CREATED);
        order1.setCreatedAt(Timestamp.valueOf("2024-06-21 00:00:00"));
        order1.setUpdatedAt(order1.getCreatedAt());
        orderRepository.save(order1);

        Order order2 = new Order();
        order2.setStatus(Status.PENDING_PAYMENT);
        order2.setCreatedAt(Timestamp.valueOf("2024-06-21 00:00:00"));
        order2.setUpdatedAt(order2.getCreatedAt());
        orderRepository.save(order2);

        Order order3 = new Order();
        order3.setStatus(Status.PAID);
        order3.setCreatedAt(Timestamp.valueOf("2024-06-21 00:00:00"));
        order3.setUpdatedAt(order3.getCreatedAt());
        orderRepository.save(order3);

        Order order4 = new Order();
        order4.setStatus(Status.ON_THE_WAY);
        order4.setCreatedAt(Timestamp.valueOf("2024-06-21 00:00:00"));
        order4.setUpdatedAt(order4.getCreatedAt());
        orderRepository.save(order4);

        Order order5 = new Order();
        order5.setStatus(Status.DELIVERED);
        order5.setCreatedAt(Timestamp.valueOf("2024-06-21 00:00:00"));
        order5.setUpdatedAt(order5.getCreatedAt());
        orderRepository.save(order5);

        Order order6 = new Order();
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

        Order updatedOrder1 = orderRepository.findById(111L).orElseThrow();
        Order updatedOrder2 = orderRepository.findById(112L).orElseThrow();
        Order updatedOrder3 = orderRepository.findById(113L).orElseThrow();
        Order updatedOrder4 = orderRepository.findById(114L).orElseThrow();
        Order updatedOrder5 = orderRepository.findById(115L).orElseThrow();
        Order updatedOrder6 = orderRepository.findById(116L).orElseThrow();

        assertThat(updatedOrder1.getStatus()).isEqualTo(Status.CREATED);
        assertThat(updatedOrder2.getStatus()).isEqualTo(Status.PENDING_PAYMENT);
        assertThat(updatedOrder3.getStatus()).isEqualTo(Status.PAID);
        assertThat(updatedOrder4.getStatus()).isEqualTo(Status.ON_THE_WAY);
        assertThat(updatedOrder5.getStatus()).isEqualTo(Status.DELIVERED);
        assertThat(updatedOrder6.getStatus()).isEqualTo(Status.CANCELED);
    }

    @Test
    void countUndeliveredOrders() {
        Integer undeliveredOrders = orderRepository.countUndeliveredOrders(startDate);

        assertThat(undeliveredOrders).isEqualTo(4);
    }
}