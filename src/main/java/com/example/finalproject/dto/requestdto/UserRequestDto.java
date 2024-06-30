package com.example.finalproject.dto.requestdto;

import com.example.finalproject.validation.groups.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto {

    @NotBlank(message = "Invalid name: Empty name", groups = {CreateGroup.class, UpdateGroup.class})
    @Size(min = 2, max = 30, message = "Invalid Name: Must be of 2 - 30 characters", groups = {CreateGroup.class, UpdateGroup.class})
    private String name;

    @NotBlank(message = "Email cannot be blank", groups = CreateGroup.class)
    @Email(message = "Invalid email", groups = CreateGroup.class)
    private String email;

    @NotBlank(message = "Invalid Phone number: Empty number", groups = {CreateGroup.class, UpdateGroup.class})
    @Pattern(regexp = "^\\+(\\d{12})$", message = "Invalid phone number", groups = {CreateGroup.class, UpdateGroup.class})
    private String phone;

    @NotBlank(message = "Password cannot be blank", groups = CreateGroup.class)
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters", groups = CreateGroup.class)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, no whitespace, and be at least 8 characters long", groups = CreateGroup.class)
    private String password;

}


