package com.example.finalproject.service;

import com.example.finalproject.config.MapperUtil;
import com.example.finalproject.dto.requestdto.*;
import com.example.finalproject.dto.responsedto.UserResponseDto;
import com.example.finalproject.entity.*;
import com.example.finalproject.entity.enums.*;
import com.example.finalproject.exception.*;
import com.example.finalproject.mapper.*;
import com.example.finalproject.repository.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
    private final Mappers mappers;
    private final CartRepository cartRepository;

    @Transactional
    public void registerUser(UserRequestDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new DataAlreadyExistsException("User already exists.");
        }
        User user = mappers.convertToUser(userRequestDto);
        user.setRole(Role.CLIENT);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        Cart cart = new Cart();
        cart.setUser(user);
        user.setCart(cart);
        userRepository.save(user);
        cartRepository.save(cart);
    }

    @Transactional
    public void registerAdmin(UserRequestDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new DataAlreadyExistsException("User already exists.");
        }
        User user = mappers.convertToUser(userRequestDto);
        user.setRole(Role.ADMINISTRATOR);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        Cart cart = new Cart();
        cart.setUser(user);
        user.setCart(cart);
        userRepository.save(user);
        cartRepository.save(cart);
    }

    @Transactional
    public void updateUser(Long id, UserRequestDto userUpdateDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundInDataBaseException("User not found in database."));
        user.setName(userUpdateDto.getName());
        user.setPhone(userUpdateDto.getPhone());
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundInDataBaseException("User not found in database."));
        if (user.getCart() != null) {
            cartRepository.delete(user.getCart());
        }
        userRepository.deleteById(id);
    }

    public UserResponseDto findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        UserResponseDto userResponseDto;
        if(user!=null) {
            userResponseDto = mappers.convertToUserResponseDto(user);
        } else {
            throw new DataNotFoundInDataBaseException("User not found in database.");
        }
        return userResponseDto;
    }

    public Set<UserResponseDto> getUsers() {
        Set<User> users = new HashSet<>();
        users.addAll(userRepository.findAll());
        return MapperUtil.convertSet(users, mappers::convertToUserResponseDto);
    }
}