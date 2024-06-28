package com.example.finalproject.dto.responsedto;

import com.example.finalproject.entity.enums.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {

    private Long userId;
    private String name;
    private String phone;
    private String email;
    private String password;
    private Role role;

}


