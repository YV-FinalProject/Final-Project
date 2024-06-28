package com.example.finalproject.controller;

import com.example.finalproject.dto.requestdto.*;
import com.example.finalproject.dto.responsedto.*;
import com.example.finalproject.service.*;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserService userService;

    @Validated
    @PostMapping("/register")

    //вариант с возвратом ResponseDto, но с полями null
//    public ResponseEntity<UserResponseDto> registerUser(@RequestBody @Valid UserRequestDto userRequestDto) {
//        UserResponseDto createdUser = userService.registerUser(userRequestDto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);

    //вариант с возвратом только нужных полей, и без ResponseDto
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        Map<String, Object> createdUser = userService.registerUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable @Positive(message = "User ID must be a positive number") Long id) {
        UserResponseDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable @Positive(message = "User ID must be a positive number") Long id, @RequestBody @Valid UserRequestDto userUpdateDto) {
        UserResponseDto updatedUser = userService.updateUser(id, userUpdateDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable @Positive(message = "User ID must be a positive number") Long id) {
        userService.deleteUser(id);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}

