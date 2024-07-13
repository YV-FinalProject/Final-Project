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
        createUser.setPhone("+111111111111");
        createUser.setPassword("Secure!1!");

        userUpdateDto = UserRequestDto.builder()
                .name("New Arne Oswald")
                .phone("+999999999999")
                .build();

        updateUser = new User();
        updateUser.setName("Old Arne Oswald");
        updateUser.setPhone("+111111111111");
    }

    @Test
    void registerUser() {
        when(userRepository.existsByEmail(userCreateDto.getEmail())).thenReturn(false);
        when(mappers.convertToUser(userCreateDto)).thenReturn(createUser);

        userService.registerUser(userCreateDto);

        verify(userRepository, times(1)).save(createUser);
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
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
    void updateUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(updateUser));

        userService.updateUser(1L, userUpdateDto);

        assertEquals(userUpdateDto.getName(), updateUser.getName());
        assertEquals(userUpdateDto.getPhone(), updateUser.getPhone());

        verify(userRepository, times(1)).save(updateUser);
    }

    @Test
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
    void deleteUser() {
        User user = new User();
        user.setUserID(1L);

        Cart cart = new Cart();
        cart.setCartId(1L);
        cart.setUser(user);
        user.setCart(cart);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(cartRepository, times(1)).delete(cart);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
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
