package com.example.finalproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemsDto {

    private Long orderItemId;

    private Long  productId;

    private Long quantity;

    private BigDecimal priceAtPurchase;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("order")
    private OrderDto orders;

}
