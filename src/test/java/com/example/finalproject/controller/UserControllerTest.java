package com.example.finalproject.controller;

import com.example.finalproject.dto.requestdto.*;
import com.example.finalproject.exception.*;
import com.example.finalproject.service.*;
import com.fasterxml.jackson.databind.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.web.servlet.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

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
    void registerUser() throws Exception {
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDto)))
                .andExpect(status().isCreated());

        verify(userServiceMock).registerUser(any(UserRequestDto.class));
    }

    @Test
    void registerUser_ShouldThrowException_WhenEmailAlreadyExists() throws Exception {
        doThrow(new DataAlreadyExistsException("User already exists"))
                .when(userServiceMock).registerUser(any(UserRequestDto.class));

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("User already exists"));

        verify(userServiceMock).registerUser(any(UserRequestDto.class));
    }

    @Test
    void updateUser() throws Exception {
        Long userId = 1L;
        mockMvc.perform(put("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDto)))
                .andExpect(status().isOk());

        verify(userServiceMock).updateUser(eq(userId), any(UserRequestDto.class));
    }

    @Test
    void updateUser_ShouldReturnNotFound_WhenUserDoesNotExist() throws Exception {
        Long userId = 1L;
        doThrow(new DataNotFoundInDataBaseException("User not found"))
                .when(userServiceMock).updateUser(anyLong(), any(UserRequestDto.class));

        mockMvc.perform(put("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found"));

        verify(userServiceMock).updateUser(eq(userId), any(UserRequestDto.class));
    }

    @Test
    void deleteUser() throws Exception {
        Long userId = 1L;
        doNothing().when(userServiceMock).deleteUser(anyLong());
        mockMvc.perform(delete("/users/{id}", userId))
                .andExpect(status().isOk());
        verify(userServiceMock).deleteUser(userId);

    }

    @Test
    void deleteUser_ShouldReturnNotFound_WhenUserDoesNotExist() throws Exception {
        Long userId = 1L;
        doThrow(new DataNotFoundInDataBaseException("User not found"))
                .when(userServiceMock).deleteUser(anyLong());

        mockMvc.perform(delete("/users/{id}", userId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found"));

        verify(userServiceMock).deleteUser(userId);
    }
}
