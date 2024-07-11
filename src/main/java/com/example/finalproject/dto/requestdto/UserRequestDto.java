package com.example.finalproject.dto.requestdto;

import com.example.finalproject.validation.groups.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto {


    @Size(min = 2, max = 30, message = "Invalid name: Must be of 2 - 30 characters", groups = {CreateGroup.class, UpdateGroup.class})
    private String name;


    @Pattern(regexp = "^[a-zA-Z0-9]+[\\w.+-]{2,30}@[a-zA-Z0-9-]{3,30}.[a-zA-Z0-9-.]{2,30}$", message = "Invalid email", groups = CreateGroup.class)
    private String email;


    @Pattern(regexp = "^\\+\\d{9,15}$", message = "Invalid phone number: Must be of 9 - 15 digits", groups = {CreateGroup.class, UpdateGroup.class})
    private String phone;


    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$",
            message = "Invalid password: Must contain at least one digit, one lowercase letter, one uppercase letter, one special character, no whitespace, and be at least 8 characters long", groups = CreateGroup.class)
    private String password;

}


