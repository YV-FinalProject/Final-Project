package com.example.finalproject.dto;

import com.example.finalproject.entity.enums.*;
import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {

    private String name;
    private String email;
    private String phone;
    private String password;
    private Long userID;
    private Role role;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("cart")
    CartDto cartDto;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("orders")
    Set<OrderResponseDto> ordersDto;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("favorites")
    Set<FavoriteDto> favoritesDto;
}


