package com.example.finalproject.dto.responsedto;

import com.example.finalproject.entity.enums.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {

    private String name;
    private String email;
    private String phone;
    private String password;
    private Long userID;
    private Role role;

}


