package com.example.finalproject.dto.requestdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartRequestDto {

    @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1")
    @Max(value = Long.MAX_VALUE, message = "Invalid ID: Id must be less than or equal to 9 223 372 036 854 775 807")
    @JsonProperty("user")
    private Long userId;

}
