package com.example.finalproject.dto.responsedto;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import java.math.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponseDto {

    private Long orderItemId;

//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonProperty("order")
//    private OrderResponseDto orderResponseDto;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("product")
    private ProductResponseDto productResponseDto;

    private BigDecimal priceAtPurchase;
    private Integer quantity;
}
