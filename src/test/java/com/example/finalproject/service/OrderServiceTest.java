package com.example.finalproject.service;

import com.example.finalproject.dto.requestdto.OrderItemRequestDto;
import com.example.finalproject.dto.requestdto.OrderRequestDto;
import com.example.finalproject.dto.responsedto.*;
import com.example.finalproject.entity.*;
import com.example.finalproject.entity.enums.DeliveryMethod;
import com.example.finalproject.entity.enums.Role;
import com.example.finalproject.entity.enums.Status;
import com.example.finalproject.exception.DataNotFoundInDataBaseException;
import com.example.finalproject.mapper.Mappers;
import com.example.finalproject.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private ProductRepository productRepositoryMock;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    public OrderRepository orderRepositoryMock;

    @Mock
    public OrderItemRepository orderItemRepositoryMock;

    @Mock
    private Mappers mappersMock;

    @InjectMocks
    private OrderService orderServiceMock;

    DataNotFoundInDataBaseException dataNotFoundInDataBaseException;

    private User user;
    private Product product;
    private Order order;
    private OrderItem orderItem;

    private ProductResponseDto productResponseDto;
    private OrderResponseDto orderResponseDto;
    private OrderItemResponseDto orderItemResponseDto;
    private UserResponseDto userResponseDto;

    private OrderItemRequestDto orderItemRequestDto, wrongOrderItemRequestDto;
    private OrderRequestDto orderRequestDto, wrongOrderRequestDto;

    Set<OrderItemRequestDto> orderItemRequestDtoSet = new HashSet<>();
    Set<OrderItemRequestDto> wrongOrderItemRequestDtoSet = new HashSet<>();

    @BeforeEach
    void setUp() {

//Entity
        user = new User(1L,
                "Arne Oswald",
                "arneoswald@example.com",
                "+496151226",
                "Pass1$trong",
                Role.CLIENT,
                null,
                null,
                null);


        order = new Order(1L,
                Timestamp.valueOf(LocalDateTime.now()),
                "Am Hofacker 64c, 9 OG, 32312, Leiteritzdorf, Sachsen-Anhalt, GERMANY",
                "+496921441",
                DeliveryMethod.COURIER_DELIVERY,
                Status.PAID,
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                user);

        product = new Product(1L,
                "Name",
                "Description",
                new BigDecimal("100.00"),
                new BigDecimal("0.00"),
                "http://localhost/img/1.jpg",
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                new Category(1L, "Category", null),
                null,
                null,
                null);

        orderItem = new OrderItem(1L,
                3,
                new BigDecimal("3.00"),
                null,
                product);


        Set<OrderItem> orderItemsSet = new HashSet<>();
        orderItemsSet.add(orderItem);
        order.setOrderItems(orderItemsSet);

        Set<Order> ordersSet = new HashSet<>();
        ordersSet.add(order);
        user.setOrders(ordersSet);

//ResponseDto
        userResponseDto = UserResponseDto.builder()
                .userID(1L)
                .name("Arne Oswald")
                .email("arneoswald@example.com")
                .phone("+496151226")
                .password("Pass1$trong")
                .role(Role.CLIENT)
                .build();

        orderResponseDto = OrderResponseDto.builder()
                .orderId(1L)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .deliveryAddress("Am Hofacker 64c, 9 OG, 32312, Leiteritzdorf, Sachsen-Anhalt, GERMANY")
                .contactPhone("+496921441")
                .deliveryMethod(DeliveryMethod.COURIER_DELIVERY)
                .status(Status.PAID)
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .orderItemsSet(null)
                .userResponseDto(userResponseDto)
                .build();

        productResponseDto = ProductResponseDto.builder()
                .productId(1L)
                .name("Name")
                .description("Description")
                .price(new BigDecimal("100.00"))
                .imageURL("http://localhost/img/1.jpg")
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .categoryResponseDto(new CategoryResponseDto(1L, "Category"))
                .build();

        orderItemResponseDto = OrderItemResponseDto.builder()
                .orderItemId(1L)
                .orderResponseDto(null)
                .productResponseDto(productResponseDto)
                .quantity(5)
                .build();

        Set<OrderItemResponseDto> orderItemResponseDtoSet = new HashSet<>();
        orderItemResponseDtoSet.add(orderItemResponseDto);
        orderResponseDto.setOrderItemsSet(orderItemResponseDtoSet);

//RequestDto
        orderItemRequestDto = OrderItemRequestDto.builder()
                .productId(1L)
                .quantity(5)
                .build();
        orderItemRequestDtoSet.add(orderItemRequestDto);

        wrongOrderItemRequestDto = OrderItemRequestDto.builder()
                .productId(66L)
                .quantity(5)
                .build();
        wrongOrderItemRequestDtoSet.add(wrongOrderItemRequestDto);

        orderRequestDto = OrderRequestDto.builder()
                .orderItemsSet(orderItemRequestDtoSet)
                .deliveryAddress("Am Hofacker 64c, 9 OG, 32312, Leiteritzdorf, Sachsen-Anhalt, GERMANY")
                .deliveryMethod("COURIER_DELIVERY")
                .build();

        wrongOrderRequestDto = OrderRequestDto.builder()
                .orderItemsSet(wrongOrderItemRequestDtoSet)
                .deliveryAddress("Am Hofacker 64c, 9 OG, 32312, Leiteritzdorf, Sachsen-Anhalt, GERMANY")
                .deliveryMethod("COURIER_DELIVERY")
                .build();

    }

    @Test
    void getOrderById() {
        Long orderId = 1L;
        Long wrongOrderId = 58L;

        when(orderRepositoryMock.findById(orderId)).thenReturn(Optional.of(order));
        when(mappersMock.convertToOrderResponseDto(any(Order.class))).thenReturn(orderResponseDto);
        when(mappersMock.convertToOrderItemResponseDto(any(OrderItem.class))).thenReturn(orderItemResponseDto);

        OrderResponseDto actualOrderResponseDto = orderServiceMock.getOrderById(orderId);

        verify(orderRepositoryMock, times(1)).findById(orderId);
        verify(mappersMock, times(1)).convertToOrderResponseDto(any(Order.class));
        verify(mappersMock, times(1)).convertToOrderItemResponseDto(any(OrderItem.class));

        assertNotNull(actualOrderResponseDto);
        assertEquals(orderResponseDto.getOrderId(), actualOrderResponseDto.getOrderId());
        assertFalse(actualOrderResponseDto.getOrderItemsSet().isEmpty());
        assertEquals(orderResponseDto.getOrderItemsSet().size(), actualOrderResponseDto.getOrderItemsSet().size());
        assertEquals(orderResponseDto.getOrderItemsSet().hashCode(), actualOrderResponseDto.getOrderItemsSet().hashCode());


        when(orderRepositoryMock.findById(wrongOrderId)).thenReturn(Optional.empty());
        dataNotFoundInDataBaseException = assertThrows(DataNotFoundInDataBaseException.class,
                () -> orderServiceMock.getOrderById(wrongOrderId));
        assertEquals("Data not found in database.", dataNotFoundInDataBaseException.getMessage());
    }

    @Test
    void getOrderHistoryByUserId() {

        Long userId = 1L;
        Long wrongUserId = 58L;

        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(user));
        when(mappersMock.convertToOrderResponseDto(any(Order.class))).thenReturn(orderResponseDto);
        when(mappersMock.convertToOrderItemResponseDto(any(OrderItem.class))).thenReturn(orderItemResponseDto);
        Set<Order> ordersSet = user.getOrders();

        Set<OrderResponseDto> actualOrderResponseDtoSet = orderServiceMock.getOrderHistoryByUserId(userId);

        verify(userRepositoryMock, times(1)).findById(userId);

        for (Order order : ordersSet) {
            verify(mappersMock, times(1)).convertToOrderResponseDto(any(Order.class));
            verify(mappersMock, times(1)).convertToOrderItemResponseDto(any(OrderItem.class));
        }

        when(userRepositoryMock.findById(wrongUserId)).thenReturn(Optional.empty());
        dataNotFoundInDataBaseException = assertThrows(DataNotFoundInDataBaseException.class,
                () -> orderServiceMock.getOrderHistoryByUserId(wrongUserId));
        assertEquals("Data not found in database.", dataNotFoundInDataBaseException.getMessage());

        when(user.getOrders() == null).thenThrow(dataNotFoundInDataBaseException);
        dataNotFoundInDataBaseException = assertThrows(DataNotFoundInDataBaseException.class,
                () -> orderServiceMock.getOrderHistoryByUserId(wrongUserId));
        assertEquals("Data not found in database.", dataNotFoundInDataBaseException.getMessage());
    }

    @Test
    void insertOrder() {

        Long userId = 1L;
        Long wrongUserId = 58L;

        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(user));

        Order orderToInsert = new Order();
        orderToInsert.setUser(user);
        orderToInsert.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        orderToInsert.setContactPhone(user.getPhoneNumber());
        orderToInsert.setDeliveryAddress(orderRequestDto.getDeliveryAddress());
        orderToInsert.setDeliveryMethod(DeliveryMethod.valueOf(orderRequestDto.getDeliveryMethod()));
        orderToInsert.setStatus(Status.CREATED);

        for (OrderItemRequestDto orderItem : orderItemRequestDtoSet) {
            when(productRepositoryMock.findById(orderItem.getProductId())).thenReturn(Optional.of(product));
        }

        Set<OrderItem> orderItemToInsertSet = new HashSet<>();
        OrderItem orderItemToInsert = new OrderItem();
        orderItemToInsert.setProduct(product);

        if (product.getDiscountPrice() == null) {
            orderItemToInsert.setPriceAtPurchase(product.getPrice());
        } else {
            orderItemToInsert.setPriceAtPurchase(product.getDiscountPrice());
        }

        orderItemToInsert.setQuantity(orderItem.getQuantity());
        orderItemToInsert.setOrder(orderToInsert);
        when(orderItemRepositoryMock.save(any(OrderItem.class))).thenReturn(orderItemToInsert);


        orderItemToInsertSet.add(orderItemToInsert);
        orderToInsert.setOrderItems(orderItemToInsertSet);
        when(orderRepositoryMock.save(any(Order.class))).thenReturn(orderToInsert);

        orderServiceMock.insertOrder(orderRequestDto, userId);

        verify(orderRepositoryMock, times(2)).save(any(Order.class));
        verify(orderItemRepositoryMock, times(1)).save(any(OrderItem.class));

        when(userRepositoryMock.findById(wrongUserId)).thenReturn(Optional.empty());
        dataNotFoundInDataBaseException = assertThrows(DataNotFoundInDataBaseException.class,
                () -> orderServiceMock.insertOrder(orderRequestDto, wrongUserId));
        assertEquals("Data not found in database.", dataNotFoundInDataBaseException.getMessage());

        for(OrderItemRequestDto wrongOrderItemRequestDto : wrongOrderItemRequestDtoSet){
            when(productRepositoryMock.findById(wrongOrderItemRequestDto.getProductId())).thenReturn(Optional.empty());
            dataNotFoundInDataBaseException = assertThrows(DataNotFoundInDataBaseException.class,
                    () -> orderServiceMock.insertOrder(wrongOrderRequestDto, userId));
            assertEquals("Data not found in database.", dataNotFoundInDataBaseException.getMessage());
        }
    }
}