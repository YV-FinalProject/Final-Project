package com.example.finalproject.dto.responsedto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponseDto {

    private Long cartItemId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("cart")
    private CartResponseDto cartResponseDto;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("product")
    private ProductResponseDto productResponseDto;

    private Integer quantity;
}
