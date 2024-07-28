package com.example.finalproject.security.jwt;

import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest {

    private String email;

    private String password;
}