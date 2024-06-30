package com.example.finalproject.service;

import com.example.finalproject.dto.requestdto.CartItemRequestDto;
import com.example.finalproject.dto.requestdto.ProductRequestDto;
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
    private CartRepository cartRepositoryMock;

    @Mock
    public CartItemRepository cartItemRepositoryMock;

    @Mock
    public OrderRepository orderRepositoryMock;

    @Mock
    private Mappers mappersMock;

    @InjectMocks
    private OrderService orderServiceMock;

    DataNotFoundInDataBaseException dataNotFoundInDataBaseException;

    private User user;
    private Cart cart;
    private CartItem cartItem;
    private Product product;
    private Order order;
    private OrderItem orderItem;

    private ProductResponseDto productResponseDto;
    private ProductRequestDto productRequestDto;
    private OrderResponseDto orderResponseDto;
    private CartItemResponseDto cartItemResponseDto;
    private CartResponseDto cartResponseDto;
    private OrderItemResponseDto orderItemResponseDto;
    private UserResponseDto userResponseDto;
    private CartItemRequestDto cartItemRequestDto, wrongCartItemRequestDto;

    @BeforeEach
    void setUp() {

        user = new User(1L,
                "Arne Oswald",
                "arneoswald@example.com",
                "+496151226",
                "Pass1$trong",
                Role.CLIENT,
                null,
                null,
                null);

//        cart = new Cart(1L, null, user);

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

//        cartItem = new CartItem(1L, product, 2, cart);
//        Set<CartItem> cartItemSet = new HashSet<>();
//        cartItemSet.add(cartItem);
//        cart.setCartItems(cartItemSet);
//        user.setCart(cart);

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
    }

    @Test
    void insertOrder() {
    }
}