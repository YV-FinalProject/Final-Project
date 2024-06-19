package com.example.finalproject.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {

    private Long cartId;

//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonProperty("users")
//    private UsersDto users;

}
