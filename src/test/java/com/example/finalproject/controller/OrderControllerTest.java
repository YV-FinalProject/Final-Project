package com.example.finalproject.controller;

import com.example.finalproject.dto.requestdto.OrderItemRequestDto;
import com.example.finalproject.dto.requestdto.OrderRequestDto;
import com.example.finalproject.dto.responsedto.*;
import com.example.finalproject.entity.enums.DeliveryMethod;
import com.example.finalproject.entity.enums.Role;
import com.example.finalproject.entity.enums.Status;
import com.example.finalproject.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderServiceMock;

    private UserResponseDto userResponseDto;
    private OrderResponseDto orderResponseDto;
    private OrderItemResponseDto orderItemResponseDto;
    private ProductResponseDto productResponseDto;
    private Set<OrderItemResponseDto> orderItemResponseDtoSet = new HashSet<>();

    private OrderRequestDto orderRequestDto;
    private OrderItemRequestDto orderItemRequestDto;
    Set<OrderItemRequestDto> orderItemRequestDtoSet = new HashSet<>();


    @BeforeEach
    void setUp() {

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
    }

    @Test
    void getOrderById() throws Exception {
        Long orderId = 1L;
        when(orderServiceMock.getOrderById(anyLong())).thenReturn(orderResponseDto);
        this.mockMvc.perform(get("/orders/{orderId}",orderId)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(orderId));
    }

    @Test
    void getOrderHistoryByUserId() throws Exception {
        Long userId = 1L;
        Set<OrderResponseDto> orderResponseDtoSet = new HashSet<>();
        orderResponseDtoSet.add(orderResponseDto);
        when(orderServiceMock.getOrderHistoryByUserId(anyLong())).thenReturn(orderResponseDtoSet);

        this.mockMvc.perform(get("/orders/history/{orderId}",userId)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..contactPhone").value(orderResponseDto.getContactPhone()));
    }

    @Test
    void insertOrder() throws Exception {
        Long userId = 1L;
        mockMvc.perform(post("/orders/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequestDto))).andDo(print())
                .andExpect(status().isOk());
    }
}