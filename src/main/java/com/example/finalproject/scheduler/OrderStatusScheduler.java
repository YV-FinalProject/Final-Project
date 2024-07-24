package com.example.finalproject.scheduler;

import com.example.finalproject.entity.enums.*;
import com.example.finalproject.repository.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.scheduling.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

import java.sql.*;
import java.util.*;
import java.util.concurrent.*;

@Component
@Slf4j
@Data
public class OrderStatusScheduler {

    private final OrderRepository orderRepository;
    private final TaskScheduler taskScheduler;

    private static final Timestamp START_DATE = Timestamp.valueOf("2024-06-20 00:00:00");

    private volatile boolean active = true;
    private ScheduledFuture<?> scheduledTask;

    @Scheduled(initialDelay = 30000, fixedRate = 30000)
    public void updateOrderStatuses() {
        if (!active) return;
        List<Status> statusesToUpdate = Arrays.asList(Status.CREATED, Status.PENDING_PAYMENT, Status.PAID, Status.ON_THE_WAY);
        Integer updatedOrders = orderRepository.updateOrderStatuses(START_DATE, statusesToUpdate);
        log.info("Number of orders updated: {}", updatedOrders);
    }
}