package com.example.finalproject.controller;

import com.example.finalproject.dto.requestdto.OrderItemRequestDto;
import com.example.finalproject.dto.requestdto.OrderRequestDto;
import com.example.finalproject.dto.responsedto.*;
import com.example.finalproject.entity.enums.DeliveryMethod;
import com.example.finalproject.entity.enums.Role;
import com.example.finalproject.entity.enums.Status;
import com.example.finalproject.security.config.SecurityConfig;
import com.example.finalproject.security.jwt.JwtProvider;
import com.example.finalproject.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;

import java.util.Set;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderServiceMock;

    @MockBean
    private JwtProvider jwtProvider;


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
                .userId(1L)
                .name("Arne Oswald")
                .email("arneoswald@example.com")
                .phone("+496151226")
                .passwordHash("ClientPass1$trong")
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
                .imageUrl("http://localhost/img/1.jpg")
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
    @WithMockUser(username = "Test User", roles = {"CLIENT","ADMINISTRATOR"})
    void getOrderById() throws Exception {
        Long orderId = 1L;
        when(orderServiceMock.getOrderById(orderId)).thenReturn(orderResponseDto);
        this.mockMvc.perform(get("/orders/{orderId}", orderId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.contactPhone").value("+496921441"));

        verify(orderServiceMock, times(1)).getOrderById(orderId);
    }

    @Test
    void shouldNotGetOrderById() throws Exception {
        Long orderId = 1L;
        when(orderServiceMock.getOrderById(orderId)).thenReturn(orderResponseDto);
        this.mockMvc.perform(get("/orders/{orderId}", orderId))
                .andDo(print())
                .andExpect(status().isForbidden());

        verify(orderServiceMock, never()).getOrderById(orderId);
    }

//    @Test
////    @WithMockUser(username = "Test User", roles = {"CLIENT","ADMINISTRATOR"})
//    void getOrderHistory() throws Exception {
//
//        String accessSecret = "qBTmv4oXFFR2GwjexDJ4t6fsIUIUhhXqlktXjXdkcyygs8nPVEwMfo29VDRRepYDVV5IkIxBMzr7OEHXEHd37w==";
////         String refreshSecret = "zL1HB3Pch05Avfynovxrf/kpF9O2m4NCWKJUjEp27s9J2jEG3ifiKCGylaZ8fDeoONSTJP/wAzKawB8F9rOMNg==";
//
//        SecretKey jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecret));
//        String jwt =  Jwts.builder()
//                .setSubject(userResponseDto.getEmail())
//                .signWith(jwtAccessSecret)
//                .claim("roles", List.of("CLIENT"))
//                .claim("name", userResponseDto.getName())
//                .compact();
//
//        JwtAuthentication jwtAuthentication = new JwtAuthentication("arneoswald@example.com", List.of("CLIENT"));
//        jwtAuthentication.setAuthenticated(true);
//
//        Set<OrderResponseDto> orderResponseDtoSet = new HashSet<>();
//        orderResponseDtoSet.add(orderResponseDto);
//
//        when(orderServiceMock.getOrderHistory("arneoswald@example.com")).thenReturn(orderResponseDtoSet);
//
//        this.mockMvc.perform(get("/orders/history")
//                        .header("authorization", jwt)
//                        .with(authentication(jwtAuthentication)))
//
//                .andExpect(status().isOk());
////                .andExpect(jsonPath("$..orderId").value(1))
////                .andExpect(jsonPath("$..contactPhone").value("+496921441"));
//
//        verify(orderServiceMock, times(1)).getOrderHistory(jwtAuthentication.getEmail());
//
//    }
//
//    @Test
//    void insertOrder() throws Exception {
//        mockMvc.perform(post("/orders")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(orderRequestDto))
//                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_CLIENT")))
//                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR"))))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void cancelOrder() throws Exception {
//        Long orderId = 1L;
//        mockMvc.perform(put("/orders//{orderId}", orderId)
//                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_CLIENT")))
//                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR"))))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
}