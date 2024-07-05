package com.example.finalproject.controller;

import com.example.finalproject.dto.requestdto.*;
import com.example.finalproject.service.*;
import com.fasterxml.jackson.databind.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userServiceMock;

    private UserRequestDto userRequestDto;

    @BeforeEach
    void setUp() {
        userRequestDto = UserRequestDto.builder()
                .name("Arne Oswald")
                .email("arnedraoswa@ldexadple.com")
                .phone("+123456789012")
                .password("Secure1!")
                .build();
    }

    @Test
    void testRegisterUser() throws Exception {
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateUser() throws Exception {
        Long userId = 1L;
        mockMvc.perform(put("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDto)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteUser() throws Exception {
        Long userId = 1L;
        doNothing().when(userServiceMock).deleteUser(anyLong());
        mockMvc.perform(delete("/users/{id}", userId))
                .andExpect(status().isOk());
    }
}
