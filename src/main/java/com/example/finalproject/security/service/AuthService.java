package com.example.finalproject.security.service;

import com.example.finalproject.dto.responsedto.UserResponseDto;
import com.example.finalproject.entity.User;
import com.example.finalproject.repository.UserRepository;
import com.example.finalproject.security.jwt.JwtAuthentication;
import com.example.finalproject.security.jwt.JwtProvider;
import com.example.finalproject.security.jwt.JwtRequest;
import com.example.finalproject.security.jwt.JwtResponse;
import com.example.finalproject.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

//    private final Map<String, String> refreshStorage = new HashMap<>();

    private final JwtProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public JwtResponse login(JwtRequest authRequest) throws AuthException {
        final UserResponseDto userResponseDto = userService.findByEmail(authRequest.getEmail());
        if (userResponseDto == null) throw new AuthException("User not found in database");

        //    if (passwordEncoder.matches(authRequest.getPassword(), userResponseDto.getPassword())) {
        if (authRequest.getPassword().equals(userResponseDto.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(userResponseDto);
            final String refreshToken = jwtProvider.generateRefreshToken(userResponseDto);
            User user = userRepository.findByEmail(authRequest.getEmail()).orElse(null);
            if (user != null) {
                user.setRefreshToken(refreshToken);
                userRepository.save(user);
            } else {
                throw new AuthException("User not found in database");
            }
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Wrong password");
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) throws AuthException {
        // Validate the provided refresh token
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            // Extract claims from the refresh token
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            // Get the user login from the token claims
            final String email = claims.getSubject();
            // Retrieve the stored refresh token for the user
            User user = userRepository.findByEmail(email).orElse(null);
            if (user != null) {
                final String savedRefreshToken = user.getRefreshToken();
                // Compare the stored refresh token with the provided token
                if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
                    // Fetch the user data
                    final UserResponseDto userResponseDto = userService.findByEmail(email);
                    if (userResponseDto == null) {
                        throw new AuthException("User not found in database");
                    }
                    // Generate a new access token
                    final String accessToken = jwtProvider.generateAccessToken(userResponseDto);
                    // Return a JwtResponse with the new access token
                    return new JwtResponse(accessToken, null);
                }
            } else {
                throw new AuthException("User not found in database");
            }
        }
        // Return a JwtResponse with null values if validation fails
        return new JwtResponse(null, null);
    }


    public JwtResponse refresh(@NonNull String refreshToken) throws AuthException {
        JwtResponse jwtResponse = null;
        // Validate the provided refresh token
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            // Extract claims from the refresh token
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            // Get the user login from the token claims
            final String email = claims.getSubject();
            // Retrieve the stored refresh token for the user
            User user = userRepository.findByEmail(email).orElse(null);
            if (user != null) {
                final String savedRefreshToken = user.getRefreshToken();

                // Compare the stored refresh token with the provided token
                if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
                    // Fetch the user data
                    final UserResponseDto userResponseDto = userService.findByEmail(email);
                    if (userResponseDto == null) {
                        throw new AuthException("User not found in database");
                    }
                    // Generate new access and refresh tokens
                    final String accessToken = jwtProvider.generateAccessToken(userResponseDto);
                    final String newRefreshToken = jwtProvider.generateRefreshToken(userResponseDto);
                    // Update the stored refresh token for the user
                    user.setRefreshToken(refreshToken);
                    userRepository.save(user);

                    // Return a JwtResponse with the new access and refresh tokens
                    jwtResponse = new JwtResponse(accessToken, newRefreshToken);
                }
            } else {
                throw new AuthException("User not found in database");
            }
        } else {
            // Throw an AuthException if validation fails
            throw new AuthException("Invalid JWT token");
        }
       return jwtResponse;
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}
