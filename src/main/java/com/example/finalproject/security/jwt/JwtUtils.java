package com.example.finalproject.security.jwt;

import io.jsonwebtoken.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Component
public class JwtUtils {

    public static JwtAuthentication generate(Claims claims) {
        String username = claims.getSubject();
        List<?> rolesObjectList = claims.get("roles", List.class);
        List<String> roles = rolesObjectList.stream()
                .map(Object::toString)
                .collect(Collectors.toList());
        return new JwtAuthentication(username, roles);
    }
}
