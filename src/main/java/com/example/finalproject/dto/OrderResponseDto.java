package com.example.finalproject.dto;

import com.example.finalproject.entity.enums.*;
import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.sql.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {

    private String deliveryAddress;
    private String deliveryMethod;
    private Long orderId;
    private Timestamp createdAt;
    private String contactPhone;
    private Status status;
    private Timestamp updatedAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("items")
    private Set<OrderItemResponseDto> itemsDto;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("user")
    private UserResponseDto userDto;
}

