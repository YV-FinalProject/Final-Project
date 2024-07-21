package com.example.finalproject.service;

import com.example.finalproject.dto.responsedto.UserResponseDto;
import com.example.finalproject.entity.User;
import com.example.finalproject.entity.enums.Role;
import com.example.finalproject.repository.UserRepository;
import com.example.finalproject.security.jwt.JwtProvider;
import com.example.finalproject.security.jwt.JwtRequest;
import com.example.finalproject.security.jwt.JwtResponse;
import com.example.finalproject.security.service.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.security.auth.message.AuthException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Key;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private UserService userServiceMock;

    @Mock
    private PasswordEncoder passwordEncoderMock;

    @Mock
    private JwtProvider jwtProviderMock;

    @InjectMocks
    private AuthService authServiceMock;

    private AuthException authException;

    private String accessToken, refreshToken, expiredRefreshToken;

    private User user;
    private UserResponseDto userResponseDto;

    @BeforeEach
    void setUp() {

        accessToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b3JzdGVuYm9ybWFubkBleGFtcGxlLmNvbSIsImV4cCI6MTcyMTM5NTk0Miwicm9sZXMiOlsiQ0xJRU5UIl0sIm5hbWUiOiJUb3JzdGVuIEJvcm1hbm4ifQ.svVnNLetGiOLooK1819aGonJv1A3JYzdnQkWIIIaC5rN-Rg6y41lk067S4KUeh_eULsUxS46vXZgdz2rmy-11Q";

        refreshToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b3JzdGVuYm9ybWFubkBleGFtcGxlLmNvbSIsImV4cCI6MTcyMzk4NzA0Mn0.-n33W1Qxu4r7EOc6aTakXRw4aFRsaJkDd7CNzHv9N2X1xsA27v-H4Ert92fHdwnu36iw6o7kh_h9kJYW5dA5MQ";

        expiredRefreshToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b3JzdGVuYm9ybWFubkBleGFtcGxlLmNvbSIsImV4cCI6MTcyMTA4NzA0Mn0.X5CgoP9OJ6xycXuHnadTovG59x-jmd65v-43ZybyzU_ickFQx-3N8SILcwjHeWMhnmVli4tLuZCkXplWVORbCA";

        userResponseDto = UserResponseDto.builder()
                .userId(1L)
                .name("Torsten Bormann")
                .email("torstenbormann@example.com")
                .phone("+496880152")
                .passwordHash("$2a$10$yovX4MDz2oZKpqq6DiWfrOkpJ3.xzCmj8cko5vNWN8kfZamm3AdTa")
                .role(Role.CLIENT)
                .build();

        user = new User(1L,
                "Torsten Bormann",
                "torstenbormann@example.com",
                "+496880152",
                "$2a$10$yovX4MDz2oZKpqq6DiWfrOkpJ3",
                Role.CLIENT,
                refreshToken,
                null,
                null,
                null);



    }

    @Test
    void login() throws AuthException {

        JwtRequest authRequest = JwtRequest.builder()
                .email("torstenbormann@example.com")
                .password("ClientPass1$trong")
                .build();

        JwtRequest wrongMailAuthRequest = JwtRequest.builder()
                .email("wrongemail@example.com")
                .password("ClientPass1$trong")
                .build();

        JwtRequest wrongPasswordAuthRequest = JwtRequest.builder()
                .email("torstenbormann@example.com")
                .password("WrongPass1$trong")
                .build();

        when(userServiceMock.getUserByEmail(authRequest.getEmail())).thenReturn(userResponseDto);
        when(passwordEncoderMock.matches(authRequest.getPassword(), userResponseDto.getPasswordHash())).thenReturn(true);
        when(jwtProviderMock.generateAccessToken(userResponseDto)).thenReturn(accessToken);
        when(jwtProviderMock.generateRefreshToken(userResponseDto)).thenReturn(refreshToken);

        when(userRepositoryMock.findByEmail(authRequest.getEmail())).thenReturn(Optional.of(user));

        JwtResponse jwtResponse = authServiceMock.login(authRequest);

        verify(userServiceMock, times(1)).getUserByEmail(authRequest.getEmail());
        verify(passwordEncoderMock, times(1)).matches(authRequest.getPassword(), userResponseDto.getPasswordHash());
        verify(jwtProviderMock, times(1)).generateAccessToken(userResponseDto);
        verify(jwtProviderMock, times(1)).generateRefreshToken(userResponseDto);
        verify(userRepositoryMock, times(1)).findByEmail(authRequest.getEmail());

        assertEquals(accessToken, jwtResponse.getAccessToken());
        assertEquals(refreshToken, jwtResponse.getRefreshToken());


        when(userServiceMock.getUserByEmail(wrongMailAuthRequest.getEmail())).thenReturn(null);
        authException = assertThrows(AuthException.class,
                () -> authServiceMock.login(wrongMailAuthRequest));
        assertEquals("User not found in database.", authException.getMessage());


        when(passwordEncoderMock.matches(wrongPasswordAuthRequest.getPassword(), userResponseDto.getPasswordHash())).thenReturn(false);
        authException = assertThrows(AuthException.class,
                () -> authServiceMock.login(wrongPasswordAuthRequest));
        assertEquals("Wrong password.", authException.getMessage());

    }

    @Test
    void getAccessToken() throws AuthException {

        Key refreshSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode("zL1HB3Pch05Avfynovxrf/kpF9O2m4NCWKJUjEp27s9J2jEG3ifiKCGylaZ8fDeoONSTJP/wAzKawB8F9rOMNg=="));

        Claims claims = Jwts.parserBuilder()
                    .setSigningKey(refreshSecretKey)
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody();

        when(jwtProviderMock.validateRefreshToken(refreshToken)).thenReturn(true);
        when(jwtProviderMock.getRefreshClaims(refreshToken)).thenReturn(claims);
        when(userRepositoryMock.findByEmail(claims.getSubject())).thenReturn(Optional.of(user));
        when(userServiceMock.getUserByEmail(claims.getSubject())).thenReturn(userResponseDto);
        when(jwtProviderMock.generateAccessToken(userResponseDto)).thenReturn(accessToken);

        JwtResponse jwtResponse = authServiceMock.getAccessToken(refreshToken);

        verify(jwtProviderMock, times(1)).validateRefreshToken(refreshToken);
        verify(jwtProviderMock, times(1)).getRefreshClaims(refreshToken);
        verify(userRepositoryMock, times(1)).findByEmail(claims.getSubject());
        verify(userServiceMock, times(1)).getUserByEmail(claims.getSubject());
        verify(jwtProviderMock, times(1)).generateAccessToken(userResponseDto);

        assertEquals(accessToken, jwtResponse.getAccessToken());
        assertNull(jwtResponse.getRefreshToken());

        when(jwtProviderMock.validateRefreshToken(expiredRefreshToken)).thenReturn(false);
        authException = assertThrows(AuthException.class,
                () -> authServiceMock.getAccessToken(expiredRefreshToken));
        assertEquals("Invalid JWT token. Please, login.", authException.getMessage());
    }

    @Test
    void refresh() throws AuthException {

        Key refreshSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode("zL1HB3Pch05Avfynovxrf/kpF9O2m4NCWKJUjEp27s9J2jEG3ifiKCGylaZ8fDeoONSTJP/wAzKawB8F9rOMNg=="));

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(refreshSecretKey)
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();

        when(jwtProviderMock.validateRefreshToken(refreshToken)).thenReturn(true);
        when(jwtProviderMock.getRefreshClaims(refreshToken)).thenReturn(claims);
        when(userRepositoryMock.findByEmail(claims.getSubject())).thenReturn(Optional.of(user));
        when(userServiceMock.getUserByEmail(claims.getSubject())).thenReturn(userResponseDto);
        when(jwtProviderMock.generateAccessToken(userResponseDto)).thenReturn(accessToken);
        when(jwtProviderMock.generateRefreshToken(userResponseDto)).thenReturn(refreshToken);

        JwtResponse jwtResponse = authServiceMock.refresh(refreshToken);

        verify(jwtProviderMock, times(1)).validateRefreshToken(refreshToken);
        verify(jwtProviderMock, times(1)).getRefreshClaims(refreshToken);
        verify(userRepositoryMock, times(1)).findByEmail(claims.getSubject());
        verify(userServiceMock, times(1)).getUserByEmail(any(String.class));
        verify(jwtProviderMock, times(1)).generateAccessToken(userResponseDto);
        verify(jwtProviderMock, times(1)).generateRefreshToken(userResponseDto);

        assertEquals(accessToken, jwtResponse.getAccessToken());
        assertEquals(refreshToken, jwtResponse.getRefreshToken());

        when(jwtProviderMock.validateRefreshToken(expiredRefreshToken)).thenReturn(false);
        authException = assertThrows(AuthException.class,
                () -> authServiceMock.getAccessToken(expiredRefreshToken));
        assertEquals("Invalid JWT token. Please, login.", authException.getMessage());
    }
}