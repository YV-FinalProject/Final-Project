package com.example.finalproject.security.controller;


import com.example.finalproject.dto.responsedto.UserResponseDto;
import com.example.finalproject.security.jwt.JwtRequest;
import com.example.finalproject.security.jwt.JwtRequestRefresh;
import com.example.finalproject.security.jwt.JwtResponse;
import com.example.finalproject.security.service.AuthService;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    /**
     * The authentication service bean for handling authentication operations.
     */
    private final AuthService authService;

    /**
     * Handles user login and returns a JWT response.
     *
     * @param authRequest the authentication request containing username and password.
     * @return a ResponseEntity containing the JWT response.
     * @throws AuthException if authentication fails.
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) throws AuthException {
        final JwtResponse token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    /**
     * Obtains a new access token using a refresh token.
     *
     * @param request the request containing the refresh token.
     * @return a ResponseEntity containing the JWT response.
     * @throws AuthException if token refresh fails.
     */
    @PostMapping("/token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody JwtRequestRefresh request) throws AuthException {
        final JwtResponse token = authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    /**
     * Obtains a new refresh token using an existing refresh token.
     *
     * @param request the request containing the refresh token.
     * @return a ResponseEntity containing the JWT response.
     * @throws AuthException if token refresh fails.
     */
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody JwtRequestRefresh request) throws AuthException {
        final JwtResponse token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

//    @PostMapping("/registration")
//    @ResponseStatus(code = HttpStatus.CREATED)
//    public ResponseEntity<UserResponseDto> register(@RequestBody UserResponseDto userCredentialsDto) throws ResponseException {
//        UserResponseDto userDto = authService.createUser(userCredentialsDto);
//        return ResponseEntity.ofNullable(userDto);
//    }
}