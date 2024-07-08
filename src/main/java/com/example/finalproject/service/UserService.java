package com.example.finalproject.service;

import com.example.finalproject.dto.requestdto.*;
import com.example.finalproject.entity.*;
import com.example.finalproject.entity.enums.*;
import com.example.finalproject.exception.*;
import com.example.finalproject.mapper.*;
import com.example.finalproject.repository.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    //    private final PasswordEncoder passwordEncoder; ////это задел на Spring Security
    private final Mappers mappers;
    private final CartRepository cartRepository;

    @Transactional
    public void registerUser(UserRequestDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new DataAlreadyExistsException("User already exists.");
        }
        User user = mappers.convertToUser(userRequestDto);
        user.setRole(Role.CLIENT);
//        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword())); ////это задел на Spring Security
        Cart cart = new Cart();
        cart.setUser(user);
        user.setCart(cart);
        userRepository.save(user);
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
}