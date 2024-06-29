package com.example.finalproject.dto.requestdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemRequestDto {

    @NotBlank(message = "Invalid Id: Empty Id")
    @Pattern(regexp = "^[^0]\\d{1,18}$", message = "Invalid Id: not a number")
    @JsonProperty("productId")
    private Long productId;

    @NotNull(message = "Invalid quantity: quantity is NULL")
    @Positive(message = "Invalid quantity: must be > 0")
    private Integer quantity;
}
