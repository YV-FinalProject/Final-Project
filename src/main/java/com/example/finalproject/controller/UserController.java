package com.example.finalproject.controller;

import com.example.finalproject.dto.requestdto.*;
import com.example.finalproject.service.*;
import com.example.finalproject.validation.groups.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

@Tag(name="User controller", description="Описание контролера")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;

    @Operation(summary = "Регистрация пользователя", description = "Позволяет зарегистрировать пользователя")
    @Validated
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody @Validated(CreateGroup.class) UserRequestDto userRequestDto) {
        userService.registerUser(userRequestDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@PathVariable @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1") @Max(value = Long.MAX_VALUE, message = "Invalid ID: Id must be less than or equal to 9 223 372 036 854 775 807") Long id,
                           @RequestBody @Validated(UpdateGroup.class) UserRequestDto userUpdateDto) {
        userService.updateUser(id, userUpdateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1") @Max(value = Long.MAX_VALUE, message = "Invalid ID: Id must be less than or equal to 9 223 372 036 854 775 807") Long id) {
        userService.deleteUser(id);
    }

}

