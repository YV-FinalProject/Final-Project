package com.example.finalproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemRequestDto {


//    @NotBlank(message = "Invalid Id: Empty Id")
//    @Pattern(regexp = "^[^0]\\d{1,18}$", message = "Invalid Id: not a number")
//    private String productId;

    @NotNull(message = "Invalid quantity: quantity is NULL")
    @Positive(message = "Invalid quantity: must be > 0")
    private Integer quantity;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("cart")
    private CartResponseDto cart;

    @NotBlank(message = "Invalid Id: Empty Id")
    @Pattern(regexp = "^[^0]\\d{1,18}$", message = "Invalid Id: not a number")
    @JsonProperty("cart")
    private Long productId;

//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonProperty("product")
//    private Product product;

}
