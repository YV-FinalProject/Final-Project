package com.example.finalproject.controller;

import com.example.finalproject.dto.requestdto.FavoriteRequestDto;
import com.example.finalproject.dto.responsedto.*;
import com.example.finalproject.entity.enums.Role;
import com.example.finalproject.service.FavoriteService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FavoriteController.class)
class FavoriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FavoriteService favoriteServiceMock;

    private UserResponseDto userResponseDto;
    private ProductResponseDto productResponseDto;
    private FavoriteResponseDto favoriteResponseDto;
    private Set<FavoriteResponseDto> favoriteResponseDtoSet = new HashSet<>();

    private FavoriteRequestDto favoriteRequestDto;

    @BeforeEach
    void setUp() {

//ResponseDto

        userResponseDto = UserResponseDto.builder()
                .userId(1L)
                .name("Arne Oswald")
                .email("arneoswald@example.com")
                .phone("+496151226")
                .password("Pass1$trong")
                .role(Role.CLIENT)
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

        favoriteResponseDto = FavoriteResponseDto.builder()
                .favoriteId(1L)
                .productResponseDto(productResponseDto)
                .userResponseDto(userResponseDto)
                .build();

        favoriteResponseDtoSet.add(favoriteResponseDto);

        //RequestDto

        favoriteRequestDto = FavoriteRequestDto.builder()
                .productId(1L)
                .build();
    }

    @Test
    void getFavoritesByUserId() throws Exception {
        Long userId = 1L;
        when(favoriteServiceMock.getFavoritesByUserId(anyLong())).thenReturn(favoriteResponseDtoSet);
        this.mockMvc.perform(get("/favorites/{userId}",userId)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..favoriteId").value(1));
    }

    @Test
    void insertFavorite() throws Exception {
        Long userId = 1L;
        mockMvc.perform(post("/favorites/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(favoriteResponseDto))).andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void deleteFavoriteByProductId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/favorites?userId=1&productId=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartItemId").doesNotExist())
                .andExpect(jsonPath("$..product.productId").doesNotExist());
    }
}