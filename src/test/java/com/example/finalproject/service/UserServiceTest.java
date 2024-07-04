package com.example.finalproject.service;

import com.example.finalproject.dto.requestdto.*;
import com.example.finalproject.entity.*;
import com.example.finalproject.exception.*;
import com.example.finalproject.mapper.*;
import com.example.finalproject.repository.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private Mappers mappers;

    @InjectMocks
    private UserService userService;

    private UserRequestDto userCreateDto;
    private User createUser;
    private UserRequestDto userUpdateDto;
    private User updateUser;

    @BeforeEach
    void setUp() {
        userCreateDto = UserRequestDto.builder()
                .name("Arne Oswald")
                .email("arnedroswa@ldexaple.com")
                .phone("+111111111111")
                .password("Secure!1!")
                .build();

        createUser = new User();
        createUser.setName("Arne Oswald");
        createUser.setEmail("arnedroswa@ldexaple.com");
        createUser.setPhoneNumber("+111111111111");
        createUser.setPassword("Secure!1!");

        userUpdateDto = UserRequestDto.builder()
                .name("New Arne Oswald")
                .phone("+999999999999")
                .build();

        updateUser = new User();
        updateUser.setName("Old Arne Oswald");
        updateUser.setPhoneNumber("+111111111111");
    }

    @Test
    @Transactional
    void registerUser() {
        when(userRepository.existsByEmail(userCreateDto.getEmail())).thenReturn(false);
        when(mappers.convertToUser(userCreateDto)).thenReturn(createUser);

        userService.registerUser(userCreateDto);

        verify(userRepository).save(createUser);
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    @Transactional
    void registerUser_ShouldThrowException_WhenEmailAlreadyExists() {
        when(userRepository.existsByEmail(userCreateDto.getEmail())).thenReturn(true);

        Exception exception = assertThrows(DataAlreadyExistsException.class, () -> {
            userService.registerUser(userCreateDto);
        });

        String expectedMessage = "User already exists";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @Transactional
    void updateUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(updateUser));

        userService.updateUser(1L, userUpdateDto);

        assertEquals(userUpdateDto.getName(), updateUser.getName());
        assertEquals(userUpdateDto.getPhone(), updateUser.getPhoneNumber());

        verify(userRepository).save(updateUser);
    }

    @Test
    @Transactional
    void updateUser_ShouldReturnNotFound_WhenUserDoesNotExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(DataNotFoundInDataBaseException.class, () -> {
            userService.updateUser(1L, userUpdateDto);
        });

        String expectedMessage = "User not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @Transactional
    void deleteUser() {
        User user = new User();
        user.setUserID(1L);

        Cart cart = new Cart();
        cart.setCartId(1L);
        cart.setUser(user);
        user.setCart(cart);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(cartRepository).delete(cart);
        verify(userRepository).deleteById(1L);
    }

    @Test
    @Transactional
    void deleteUser_ShouldReturnNotFound_WhenUserDoesNotExists() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(DataNotFoundInDataBaseException.class, () -> {
            userService.deleteUser(1L);
        });

        String expectedMessage = "User not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        verify(cartRepository, never()).delete(any(Cart.class));
        verify(userRepository, never()).deleteById(anyLong());
    }
}
