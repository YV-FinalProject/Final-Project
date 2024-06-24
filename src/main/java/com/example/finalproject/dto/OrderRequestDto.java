package com.example.finalproject.dto;

import jakarta.validation.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDto {

    @Valid
    @NotEmpty(message = "Items list cannot be empty")
    private Set<OrderItemResponseDto> items;

    @NotBlank(message = "Delivery address cannot be blank")
    @Size(max = 255, message = "Delivery address must be less than or equal to 255 characters")
    @Pattern(regexp = "^[a-zA-Z0-9.,\\s]+$", message = "Delivery address contains invalid characters")
    private String deliveryAddress;

    @NotBlank(message = "Delivery method cannot be blank")
    @Size(min = 3, max = 30, message = "Invalid Delivery method: Must be one of: COURIER_DELIVERY or CUSTOMER_PICKUP")
    private String deliveryMethod;
}

