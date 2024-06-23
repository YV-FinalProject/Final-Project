package com.example.finalproject.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.math.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponseDto {

    private Long productId;
    private Integer quantity;
    private Long orderItemId;
    private BigDecimal priceAtPurchase;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("order")
    private OrderResponseDto orderDto;
}
