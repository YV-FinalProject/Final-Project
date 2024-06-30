package com.example.finalproject.controller;

import com.example.finalproject.dto.requestdto.*;
import com.example.finalproject.service.*;
import com.example.finalproject.validation.groups.*;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;

    @Validated
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody @Validated(CreateGroup.class) UserRequestDto userRequestDto) {
        userService.registerUser(userRequestDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@PathVariable @Positive(message = "User ID must be a positive number") Long id, @RequestBody @Validated(UpdateGroup.class) UserRequestDto userUpdateDto) {
        userService.updateUser(id, userUpdateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable @Positive(message = "User ID must be a positive number") Long id) {
        userService.deleteUser(id);
    }
}

