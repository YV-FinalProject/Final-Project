package com.example.finalproject.scheduler;

import com.example.finalproject.entity.enums.*;
import com.example.finalproject.repository.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.scheduling.*;

import java.sql.*;
import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderStatusSchedulerTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private TaskScheduler taskScheduler;

    @InjectMocks
    private OrderStatusScheduler orderStatusScheduler;

    private static final Timestamp START_DATE = Timestamp.valueOf("2024-06-20 00:00:00");
    private static final List<Status> STATUSES_TO_UPDATE = Arrays.asList(Status.CREATED, Status.PENDING_PAYMENT, Status.PAID, Status.ON_THE_WAY);

    @BeforeEach
    void setUp() {
        orderStatusScheduler = new OrderStatusScheduler(orderRepository, taskScheduler);
        orderStatusScheduler.setActive(true);
    }

    @Test
    void updateOrderStatuses() {
        when(orderRepository.updateOrderStatuses(eq(START_DATE), eq(STATUSES_TO_UPDATE))).thenReturn(5);

        orderStatusScheduler.updateOrderStatuses();

        verify(orderRepository, times(1)).updateOrderStatuses(eq(START_DATE), eq(STATUSES_TO_UPDATE));
        verifyNoMoreInteractions(orderRepository);
    }
}
