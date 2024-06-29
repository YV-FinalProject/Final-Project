package com.example.finalproject.dto.responsedto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteResponseDto {

    private Long favoriteId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("product")
    private ProductResponseDto productResponseDto;

//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonProperty("user")
//    private UserResponseDto userResponseDto;
}
