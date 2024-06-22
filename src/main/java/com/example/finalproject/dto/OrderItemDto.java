package com.example.finalproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {

    @NotBlank(message = "Invalid Id: Empty Id")
    @Pattern(regexp = "^[^0]\\d{1,18}$", message = "Invalid Id: not a number")
    private Long productId;

    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be positive")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Max(value = 1000, message = "Quantity cannot exceed 1000")
    private Integer quantity;

    private Long orderItemId;
    private BigDecimal priceAtPurchase;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("order")
    private OrderDto order;
}
