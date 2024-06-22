package com.example.finalproject.dto;

import com.example.finalproject.entity.Cart;
import com.example.finalproject.entity.Favorite;
import com.example.finalproject.entity.Order;
import com.example.finalproject.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    @NotBlank(message = "Invalid name: Empty name")
    @Size(min = 2, max = 30, message = "Invalid Name: Must be of 2 - 30 characters")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Invalid Phone number: Empty number")
    @Pattern(regexp = "^\\+(\\d{12})$", message = "Invalid phone number")
    private String phone;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$)",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, and no whitespace")
    private String password;

    private Long userID;

    @NotNull(message = "Role cannot be null")
    private Role role;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("cart")
    CartDto cartDto;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("orders")
    List<OrderDto> ordersDTO;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("favorites")
    List<FavoriteDto> favoritesDTO;
}


