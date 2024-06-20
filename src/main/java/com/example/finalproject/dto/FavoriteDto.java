package com.example.finalproject.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteDto {

    private Long favoriteId;
    private Long  productId;

//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonProperty("user")
//    private UserDto user;

}
