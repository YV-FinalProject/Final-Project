package com.example.finalproject.security.service;

import com.example.finalproject.dto.responsedto.*;
import com.example.finalproject.security.jwt.*;
import com.example.finalproject.service.*;
import io.jsonwebtoken.*;
import jakarta.security.auth.message.*;
import lombok.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public JwtResponse login(JwtRequest authRequest) throws AuthException {
        final UserResponseDto userDto = userService.getByEmail(authRequest.getEmail());
        if (userDto == null) {
            throw new AuthException("User is not found");
        }

        if (passwordEncoder.matches(authRequest.getPassword(), userDto.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(userDto);
            final String refreshToken = jwtProvider.generateRefreshToken(userDto);
            refreshStorage.put(userDto.getEmail(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Wrong password");
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String savedRefreshToken = refreshStorage.get(login);
            if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
                final UserResponseDto userDto = userService.getByEmail(login);
                if (userDto == null) {
                    throw new AuthException("User is not found");
                }
                final String accessToken = jwtProvider.generateAccessToken(userDto);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String savedRefreshToken = refreshStorage.get(login);
            if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
                final UserResponseDto userDto = userService.getByEmail(login);
                if (userDto == null) {
                    throw new AuthException("User is not found");
                }
                final String accessToken = jwtProvider.generateAccessToken(userDto);
                final String newRefreshToken = jwtProvider.generateRefreshToken(userDto);
                refreshStorage.put(userDto.getEmail(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Invalid JWT token");
    }
}
