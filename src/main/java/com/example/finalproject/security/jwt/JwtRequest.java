package com.example.finalproject.security.jwt;

import lombok.*;

@Setter
@Getter
public class JwtRequest {

    private String email;
    private String password;
}
