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
    CartResponseDto cartDto;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Set<OrderResponseDto> ordersDto;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Set<FavoriteResponseDto> favoritesDto;
}


