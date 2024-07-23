package com.example.finalproject.controller;

import com.example.finalproject.security.config.SecurityConfig;
import com.example.finalproject.security.controller.AuthController;
import com.example.finalproject.security.jwt.*;
import com.example.finalproject.security.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authServiceMock;

    @MockBean
    private JwtProvider jwtProvider;

    JwtResponse jwtResponse;

    JwtRequest jwtRequest;

    JwtRequestRefresh requestRefresh;

    @BeforeEach
    void setUp() {

        jwtResponse = JwtResponse.builder()
                .accessToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b3JzdGVuYm9ybWFubkBleGFtcGxlLmNvbSIsImV4cCI6MTcyMTY3MjU0NCwicm9sZXMiOlsiQ0xJRU5UIl0sIm5hbWUiOiJUb3JzdGVuIEJvcm1hbm4ifQ.fNUyCRe40xEjiVPpqlIfZp5raFeWxUqXxTBwEgerlOU_pv3uqC5IzhNdxpYa1oJfi-hEOLX4JwNNRBniGwiYdQ")
                .refreshToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b3JzdGVuYm9ybWFubkBleGFtcGxlLmNvbSIsImV4cCI6MTcyNDI2MzY0NH0.yZLozhgKASNhrsux9Ej9E-ozdLDODsUr0NrS-JpvoNUzPczC7ttMlX6AjkZlINUixYQ_yxWBhxlhwD84RW1k9w")
                .build();

        jwtRequest = JwtRequest.builder()
                .email("torstenbormann@example.com")
                .password("ClientPass1$trong")
                .build();

        requestRefresh = JwtRequestRefresh.builder()
                .refreshToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b3JzdGVuYm9ybWFubkBleGFtcGxlLmNvbSIsImV4cCI6MTcyNDI2MzY0NH0.yZLozhgKASNhrsux9Ej9E-ozdLDODsUr0NrS-JpvoNUzPczC7ttMlX6AjkZlINUixYQ_yxWBhxlhwD84RW1k9w")
                .build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void login() throws Exception {
        when(authServiceMock.login(jwtRequest)).thenReturn(jwtResponse);
        this.mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jwtRequest)))
                .andDo(print())
                .andExpect(status().isOk());

        verify(authServiceMock, times(1)).login(jwtRequest);
    }

    @Test
    void getNewAccessToken() throws Exception {
        when(authServiceMock.getAccessToken(requestRefresh.refreshToken)).thenReturn(jwtResponse);
        this.mockMvc.perform(post("/auth/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestRefresh)))
                .andDo(print())
                .andExpect(status().isOk());

        verify(authServiceMock, times(1)).getAccessToken(requestRefresh.refreshToken);
    }

    @Test
    @WithMockUser(username = "Test User", roles = {"CLIENT","ADMINISTRATOR"})
    void getNewRefreshToken() throws Exception {
        when(authServiceMock.refresh(requestRefresh.refreshToken)).thenReturn(jwtResponse);
        this.mockMvc.perform(post("/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestRefresh)))
                .andDo(print())
                .andExpect(status().isOk());

        verify(authServiceMock, times(1)).refresh(requestRefresh.refreshToken);
    }

    @Test
    void shouldNotGetNewRefreshToken() throws Exception {
        when(authServiceMock.refresh(requestRefresh.refreshToken)).thenReturn(jwtResponse);
        this.mockMvc.perform(post("/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestRefresh)))
                .andDo(print())
                .andExpect(status().isForbidden());

        verify(authServiceMock, never()).refresh(requestRefresh.refreshToken);
    }
}