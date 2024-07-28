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
    private UserRepository userRepositoryMock;

    @Mock
    public OrderRepository orderRepositoryMock;

    @Mock
    public OrderItemRepository orderItemRepositoryMock;

    @Mock
    private ProductRepository productRepositoryMock;

    @Mock
    private CartRepository cartRepositoryMock;

    @Mock
    private CartItemRepository cartItemRepositoryMock;

    @InjectMocks
    private OrderService orderServiceMock;

    @Mock
    private Mappers mappersMock;

    DataNotFoundInDataBaseException dataNotFoundInDataBaseException;

    private User user;
    private Order order;
    private OrderItem orderItem;
    private Product product;
    private Cart cart;
    private CartItem cartItem;

    private OrderResponseDto orderResponseDto;
    private OrderItemResponseDto orderItemResponseDto;


    Set<OrderItem> orderItemsSet = new HashSet<>();
    Set<Order> ordersSet = new HashSet<>();
    Set<CartItem> cartItemSet = new HashSet<>();

    private OrderRequestDto orderRequestDto, wrongOrderRequestDto;
    private OrderItemRequestDto orderItemRequestDto, wrongOrderItemRequestDto;

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
                null,
                null);


        order = new Order(1L,
                Timestamp.valueOf(LocalDateTime.now()),
                "Am Hofacker 64c, 9 OG, 32312, Leiteritzdorf, Sachsen-Anhalt, GERMANY",
                "+496921441",
                DeliveryMethod.COURIER_DELIVERY,
                Status.CREATED,
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

        cartItem = new CartItem(1L,
                product,
                25,
                cart);

        cart = new Cart(1L,
                null,
                user);

        orderItemsSet.add(orderItem);
        order.setOrderItems(orderItemsSet);

        ordersSet.add(order);
        user.setOrders(ordersSet);

        cartItemSet.add(cartItem);
        cart.setCartItems(cartItemSet);
        user.setCart(cart);




//ResponseDto
        orderResponseDto = OrderResponseDto.builder()
                .orderId(1L)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .deliveryAddress("Am Hofacker 64c, 9 OG, 32312, Leiteritzdorf, Sachsen-Anhalt, GERMANY")
                .contactPhone("+496921441")
                .deliveryMethod(DeliveryMethod.COURIER_DELIVERY)
                .status(Status.PAID)
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .orderItemsSet(null)
                .userResponseDto(new UserResponseDto())
                .build();


        orderItemResponseDto = OrderItemResponseDto.builder()
                .orderItemId(1L)
                .orderResponseDto(null)
                .productResponseDto(new ProductResponseDto())
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

        orderRequestDto = OrderRequestDto.builder()
                .orderItemsSet(orderItemRequestDtoSet)
                .deliveryAddress("Am Hofacker 64c, 9 OG, 32312, Leiteritzdorf, Sachsen-Anhalt, GERMANY")
                .deliveryMethod("COURIER_DELIVERY")
                .build();


        wrongOrderItemRequestDto = OrderItemRequestDto.builder()
                .productId(66L)
                .quantity(5)
                .build();
        wrongOrderItemRequestDtoSet.add(wrongOrderItemRequestDto);

        wrongOrderRequestDto = OrderRequestDto.builder()
                .orderItemsSet(wrongOrderItemRequestDtoSet)
                .deliveryAddress("Am Hofacker 64c, 9 OG, 32312, Leiteritzdorf, Sachsen-Anhalt, GERMANY")
                .deliveryMethod("COURIER_DELIVERY")
                .orderItemsSet(wrongOrderItemRequestDtoSet)
                .build();
    }

    @Test
    void getOrderById() {

        String email = "arneoswald@example.com";
        String wrongEmail = "wrongemail@example.com";

        Long orderId = 1L;

        when(userRepositoryMock.findByEmail(email)).thenReturn(Optional.of(user));
        when(mappersMock.convertToOrderResponseDto(any(Order.class))).thenReturn(orderResponseDto);
        when(mappersMock.convertToOrderItemResponseDto(any(OrderItem.class))).thenReturn(orderItemResponseDto);

        OrderResponseDto actualOrderResponseDto = orderServiceMock.getOrderById(orderId, email);

        verify(userRepositoryMock, times(1)).findByEmail(email);
        verify(mappersMock, times(1)).convertToOrderResponseDto(any(Order.class));
        verify(mappersMock, times(1)).convertToOrderItemResponseDto(any(OrderItem.class));

        assertNotNull(actualOrderResponseDto);
        assertEquals(orderResponseDto.getOrderId(), actualOrderResponseDto.getOrderId());
        assertFalse(actualOrderResponseDto.getOrderItemsSet().isEmpty());
        assertEquals(orderResponseDto.getOrderItemsSet().size(), actualOrderResponseDto.getOrderItemsSet().size());
        assertEquals(orderResponseDto.getOrderItemsSet().hashCode(), actualOrderResponseDto.getOrderItemsSet().hashCode());


        when(userRepositoryMock.findByEmail(wrongEmail)).thenReturn(Optional.empty());
        dataNotFoundInDataBaseException = assertThrows(DataNotFoundInDataBaseException.class,
                () -> orderServiceMock.getOrderById(orderId, wrongEmail));
        assertEquals("User not found in database.", dataNotFoundInDataBaseException.getMessage());

    }

    @Test
    void getOrderHistory() {

        String email = "arneoswald@example.com";
        String wrongEmail = "wrongemail@example.com";

        when(userRepositoryMock.findByEmail(email)).thenReturn(Optional.of(user));
        when(mappersMock.convertToOrderResponseDto(any(Order.class))).thenReturn(orderResponseDto);
        when(mappersMock.convertToOrderItemResponseDto(any(OrderItem.class))).thenReturn(orderItemResponseDto);
        Set<OrderResponseDto> ordersResponseDtoSet = new HashSet<>();
        ordersResponseDtoSet.add(orderResponseDto);

        Set<OrderResponseDto> actualOrderResponseDtoSet = orderServiceMock.getOrderHistory(email);

        verify(userRepositoryMock, times(1)).findByEmail(email);
        verify(mappersMock, times(1)).convertToOrderResponseDto(any(Order.class));
        verify(mappersMock, times(1)).convertToOrderItemResponseDto(any(OrderItem.class));

        assertFalse(actualOrderResponseDtoSet.isEmpty());
        assertEquals(ordersResponseDtoSet.size(), actualOrderResponseDtoSet.size());
        assertEquals(ordersResponseDtoSet.hashCode(), actualOrderResponseDtoSet.hashCode());


        when(userRepositoryMock.findByEmail(wrongEmail)).thenReturn(Optional.empty());
        dataNotFoundInDataBaseException = assertThrows(DataNotFoundInDataBaseException.class,
                () -> orderServiceMock.getOrderHistory(wrongEmail));
        assertEquals("User not found in database.", dataNotFoundInDataBaseException.getMessage());


        user.setOrders(null);
        when(userRepositoryMock.findByEmail(email)).thenReturn(Optional.of(user));
        dataNotFoundInDataBaseException = assertThrows(DataNotFoundInDataBaseException.class,
                () -> orderServiceMock.getOrderHistory(email));
        assertEquals("No orders were placed yet.", dataNotFoundInDataBaseException.getMessage());
    }

    @Test
    void insertOrder() {

        String email = "arneoswald@example.com";
        String wrongEmail = "wrongemail@example.com";

        when(userRepositoryMock.findByEmail(email)).thenReturn(Optional.of(user));
        when(productRepositoryMock.findById(orderRequestDto.getOrderItemsSet().iterator().next().getProductId())).thenReturn(Optional.of(product));
        when(orderRepositoryMock.save(any(Order.class))).thenReturn(order);
        when(cartRepositoryMock.findById(user.getCart().getCartId())).thenReturn(Optional.of(cart));

        orderServiceMock.insertOrder(orderRequestDto, email);

        verify(orderRepositoryMock, times(2)).save(any(Order.class));
        verify(orderItemRepositoryMock, times(1)).save(any(OrderItem.class));
        verify(cartItemRepositoryMock, times(1)).deleteById(cart.getCartItems().iterator().next().getCartItemId());


        when(userRepositoryMock.findByEmail(wrongEmail)).thenReturn(Optional.empty());
        dataNotFoundInDataBaseException = assertThrows(DataNotFoundInDataBaseException.class,
                () -> orderServiceMock.insertOrder(orderRequestDto, wrongEmail));
        assertEquals("User not found in database.", dataNotFoundInDataBaseException.getMessage());


        when(productRepositoryMock.findById(wrongOrderRequestDto.getOrderItemsSet().iterator().next().getProductId())).thenReturn(Optional.empty());
        dataNotFoundInDataBaseException = assertThrows(DataNotFoundInDataBaseException.class,
                () -> orderServiceMock.insertOrder(wrongOrderRequestDto, email));
        assertEquals("Product not found in database.", dataNotFoundInDataBaseException.getMessage());


        when(cartRepositoryMock.findById(user.getCart().getCartId())).thenReturn(Optional.empty());
        dataNotFoundInDataBaseException = assertThrows(DataNotFoundInDataBaseException.class,
                () -> orderServiceMock.insertOrder(orderRequestDto, email));
        assertEquals("Cart not found in database.", dataNotFoundInDataBaseException.getMessage());
    }


    @Test
    void cancelOrder() {
        Long orderId = 1L;

        String email = "arneoswald@example.com";
        String wrongEmail = "wrongemail@example.com";

        when(userRepositoryMock.findByEmail(email)).thenReturn(Optional.of(user));

        orderServiceMock.cancelOrder(orderId, email);

        verify(userRepositoryMock, times(1)).findByEmail(email);
        verify(orderRepositoryMock, times(1)).save(any(Order.class));

        when(userRepositoryMock.findByEmail(wrongEmail)).thenReturn(Optional.empty());
        dataNotFoundInDataBaseException = assertThrows(DataNotFoundInDataBaseException.class,
                () -> orderServiceMock.cancelOrder(orderId, wrongEmail));
        assertEquals("User not found in database.", dataNotFoundInDataBaseException.getMessage());

    }

}