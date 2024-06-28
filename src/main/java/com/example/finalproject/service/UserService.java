package com.example.finalproject.service;

import com.example.finalproject.dto.requestdto.*;
import com.example.finalproject.dto.responsedto.*;
import com.example.finalproject.entity.*;
import com.example.finalproject.exception.*;
import com.example.finalproject.repository.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder; ////это задел на Spring Security

//    //это вариант возврата общей Dto c ненужными полями в которых будет null
//    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
//        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
//            throw new UserAlreadyExistsException("User already exists");
//        }
//        User user = new User();
//        user.setName(userRequestDto.getName());
//        user.setPhoneNumber(userRequestDto.getPhone());
//        user.setEmail(userRequestDto.getEmail());
////        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
//        User savedUser = userRepository.save(user);
//        return UserResponseDto.builder()
//                .name(savedUser.getName())
//                .phone(savedUser.getPhoneNumber())
//                .email(savedUser.getEmail())
//                .build();
//    }

    //это вариант возврата мапы только с нужным кол-вом полей согласно API
    public Map<String, Object> registerUser(UserRequestDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new UserAlreadyExistsException("User already exists");
        }
        User user = new User();
        user.setName(userRequestDto.getName());
        user.setPhoneNumber(userRequestDto.getPhone());
        user.setEmail(userRequestDto.getEmail());
//        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword())); ////это задел на Spring Security
        user.setPassword(userRequestDto.getPassword());
        User savedUser = userRepository.save(user);
        Map<String, Object> responseDto = new LinkedHashMap<>();
        responseDto.put("name", savedUser.getName());
        responseDto.put("email", savedUser.getEmail());
        responseDto.put("phone", savedUser.getPhoneNumber());
        return responseDto;
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserResponseDto.builder()
                .userId(user.getUserID())
                .name(user.getName())
                .phone(user.getPhoneNumber())
                .email(user.getEmail())
                .build();
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userUpdateDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (userUpdateDto.getName() != null) {
            user.setName(userUpdateDto.getName());
        }
        if (userUpdateDto.getPhone() != null) {
            user.setPhoneNumber(userUpdateDto.getPhone());
        }
        User updatedUser = userRepository.save(user);
        return UserResponseDto.builder()
                .name(updatedUser.getName())
                .phone(updatedUser.getPhoneNumber())
                .build();
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> UserResponseDto.builder()
                        .userId(user.getUserID())
                        .name(user.getName())
                        .phone(user.getPhoneNumber())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .build())
                .collect(Collectors.toList());
    }
}
