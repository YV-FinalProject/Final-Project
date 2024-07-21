package com.example.finalproject.dto.responsedto;

import com.example.finalproject.entity.enums.*;
import com.fasterxml.jackson.annotation.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto {

    private Long userId;
    private String name;
    private String phone;
    private String email;
    private String passwordHash;
    private Role role;

}


