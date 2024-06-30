package com.example.finalproject.controller;

import com.example.finalproject.dto.requestdto.CartItemRequestDto;
import com.example.finalproject.dto.responsedto.*;
import com.example.finalproject.entity.enums.Role;
import com.example.finalproject.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CartService cartServiceMock;

    private UserResponseDto userResponseDto;
    private CartResponseDto cartResponseDto;
    private CartItemResponseDto cartItemResponseDto;
    private ProductResponseDto productResponseDto;
    private Set<CartItemResponseDto> cartItemResponseDtoSet = new HashSet<>();

    private CartItemRequestDto cartItemRequestDto;

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

        cartResponseDto = CartResponseDto.builder()
                .cartId(1L)
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

        cartItemResponseDto = CartItemResponseDto.builder()
                .cartItemId(1L)
                .cartResponseDto(cartResponseDto)
                .productResponseDto(productResponseDto)
                .quantity(5)
                .build();

        cartItemResponseDtoSet.add(cartItemResponseDto);

//RequestDto
        cartItemRequestDto = CartItemRequestDto.builder()
                .productId(1L)
                .quantity(5)
                .build();

    }

    @Test
    void getCartItemsByUserId() throws Exception {
        Long userId = 1L;

        when(cartServiceMock.getCartItemsByUserId(anyLong())).thenReturn(cartItemResponseDtoSet);

        this.mockMvc.perform(get("/cart/{userId}",userId)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..cartItemId").value(1))
                .andExpect(jsonPath("$..product.productId").value(1));
    }

    @Test
    void insertCartItem() throws Exception  {
        Long userId = 1L;
        mockMvc.perform(post("/cart/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartItemRequestDto))).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteCarItemByProductId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/cart?userId=1&productId=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..cartItemId").doesNotExist())
                .andExpect(jsonPath("$..product.productId").doesNotExist());
    }
}