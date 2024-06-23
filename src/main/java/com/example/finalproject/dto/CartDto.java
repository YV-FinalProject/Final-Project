package com.example.finalproject.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {

    private Long cartId;

//    @JsonProperty("items")
//    private Set<CartItem> cartItems = new HashSet<>();

//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonProperty("user")
//    private UserDto uses;



}
