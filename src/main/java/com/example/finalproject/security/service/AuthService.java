package com.example.finalproject.security.service;

import com.example.finalproject.dto.responsedto.UserResponseDto;
import com.example.finalproject.entity.User;
import com.example.finalproject.repository.UserRepository;
import com.example.finalproject.security.jwt.JwtProvider;
import com.example.finalproject.security.jwt.JwtRequest;
import com.example.finalproject.security.jwt.JwtResponse;
import com.example.finalproject.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
        final UserResponseDto userResponseDto = userService.getUserByEmail(authRequest.getEmail());
        if (userResponseDto == null) throw new AuthException("User not found in database");

        if (passwordEncoder.matches(authRequest.getPassword(), userResponseDto.getPassword())) {
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

        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();

            User user = userRepository.findByEmail(email).orElse(null);
            if (user != null) {
                final String savedRefreshToken = user.getRefreshToken();
                if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
                    final UserResponseDto userResponseDto = userService.getUserByEmail(email);
                    if (userResponseDto == null) {
                        throw new AuthException("User not found in database");
                    }

                    final String accessToken = jwtProvider.generateAccessToken(userResponseDto);
                    return new JwtResponse(accessToken, null);
                }
            } else {
                throw new AuthException("User not found in database");
            }
        }
        // Return a JwtResponse with null values if validation fails
        return new JwtResponse(null, null);// может лучше выбросить ошибку, что токен не прошел валидацию???
    }


    public JwtResponse refresh(@NonNull String refreshToken) throws AuthException {
        JwtResponse jwtResponse = null;
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();

            User user = userRepository.findByEmail(email).orElse(null);
            if (user != null) {
                final String savedRefreshToken = user.getRefreshToken();

                if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
                    final UserResponseDto userResponseDto = userService.getUserByEmail(email);
                    if (userResponseDto == null) {
                        throw new AuthException("User not found in database");
                    }
                    final String newAccessToken = jwtProvider.generateAccessToken(userResponseDto);
                    final String newRefreshToken = jwtProvider.generateRefreshToken(userResponseDto);

                    user.setRefreshToken(refreshToken);
                    userRepository.save(user);
                    jwtResponse = new JwtResponse(newAccessToken, newRefreshToken);
                }
            } else {
                throw new AuthException("User not found in database");
            }
        } else {
            throw new AuthException("Invalid JWT token");
        }
        return jwtResponse;
    }

//    public JwtAuthentication getAuthInfo() {
//        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
//    }

}
