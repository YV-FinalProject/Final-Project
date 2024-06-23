package com.example.finalproject.dto;

import com.example.finalproject.entity.enums.*;
import com.fasterxml.jackson.annotation.*;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.sql.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    @Valid
    @NotEmpty(message = "Items list cannot be empty")
    private Set<OrderItemDto> items;

    @NotBlank(message = "Delivery address cannot be blank")
    @Size(max = 255, message = "Delivery address must be less than or equal to 255 characters")
    @Pattern(regexp = "^[a-zA-Z0-9.,\\s]+$", message = "Delivery address contains invalid characters")
    private String deliveryAddress;

    @NotBlank(message = "Delivery method cannot be blank")
    @Size(min = 3, max = 30, message = "Invalid Delivery method: Must be of 3 - 30 characters")
    private String deliveryMethod;

    private Long orderId;
    private Timestamp createdAt;
    private String contactPhone;
    private Status status;
    private Timestamp updatedAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("user")
    private UserDto user;
}

