package com.example.finalproject.controller;

import com.example.finalproject.dto.requestdto.*;
import com.example.finalproject.dto.responsedto.UserResponseDto;
import com.example.finalproject.service.*;
import com.example.finalproject.validation.groups.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tag(name = "User controller", description = "Controller fo managing user's accounts")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;

    @Operation(summary = "User registration", description = "Provides functionality for registering a new user")
    @Validated
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody @Validated(CreateGroup.class) UserRequestDto userRequestDto) {
        userService.registerUser(userRequestDto);
    }

    @Operation(summary = "Administrator registration", description = "Provides functionality for registering a new administrator")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @Validated
    @PostMapping("/registerAdmin")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAdmin(@RequestBody @Validated(CreateGroup.class) UserRequestDto userRequestDto) {
        userService.registerAdmin(userRequestDto);
    }

    @Operation(summary = "Updating user's account", description = "Provides functionality for updating information in user's account")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@PathVariable
                               @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1")
                               @Parameter(description = "User identifier") Long id,

                           @RequestBody @Validated(UpdateGroup.class) UserRequestDto userUpdateDto) {

        userService.updateUser(id, userUpdateDto);
    }

    @Operation(summary = "Deleting user's account", description = "Provides functionality for deleting a user's account")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable
                               @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1")
                               @Parameter(description = "Category identifier") Long id) {
        userService.deleteUser(id);
    }

    @Operation(summary = "Getting all users", description = "Provides functionality for getting all registered users (including administrators)")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<UserResponseDto> getUsers() {
        return userService.getUsers();
    }

}

