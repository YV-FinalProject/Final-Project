package com.example.finalproject.dto.requestdto;

import com.example.finalproject.validation.annotation.*;
import com.fasterxml.jackson.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDto {

    @JsonProperty("items")
    @NotEmpty(message = "Items list cannot be empty")
    private Set<@Valid OrderItemRequestDto> orderItemsSet;


    @Size(min = 1, max = 255, message = "Delivery address must be less than or equal to 255 characters")
    private String deliveryAddress;

    @ValidDeliveryMethod
    private String deliveryMethod;
}

