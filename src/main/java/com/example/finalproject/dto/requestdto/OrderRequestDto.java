package com.example.finalproject.dto.requestdto;

import com.example.finalproject.validation.annotation.*;
import com.fasterxml.jackson.annotation.*;
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
    private Set<OrderItemRequestDto> orderItemsSet;

    @NotBlank(message = "Delivery address cannot be blank")
    @Size(max = 255, message = "Delivery address must be less than or equal to 255 characters")
    @Pattern(regexp = "^[a-zA-Z0-9.,\\s]+$", message = "Delivery address contains invalid characters")
    private String deliveryAddress;

    @ValidDeliveryMethod
    @NotBlank(message = "Delivery method cannot be blank")
    @Size(min = 3, max = 30, message = "Invalid Delivery method: Must be one of: COURIER_DELIVERY or CUSTOMER_PICKUP")
//найти нормальную валидацию для Enum
    private String deliveryMethod;
}

