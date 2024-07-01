package com.example.finalproject.dto.requestdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteRequestDto {

//    @NotNull(message = "Invalid Id: Empty Id")
//    @Pattern(regexp = "^[^0]\\d{1,18}$", message = "Invalid Id: not a number")
    @JsonProperty("productId")
    private Long productId;

}
