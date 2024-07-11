package com.example.finalproject.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.*;
import com.example.finalproject.dto.requestdto.FavoriteRequestDto;
import com.example.finalproject.dto.responsedto.FavoriteResponseDto;
import com.example.finalproject.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tag(name="Favorite controller", description="Описание контролера")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;

    @GetMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Set<FavoriteResponseDto> getFavoritesByUserId(@PathVariable @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1") @Max(value = Long.MAX_VALUE, message = "Invalid ID: Id must be less than or equal to 9 223 372 036 854 775 807") Long userId) {
        return favoriteService.getFavoritesByUserId(userId);
    }

    @PostMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void insertFavorite(@RequestBody @Valid FavoriteRequestDto favoriteRequestDto,
                               @PathVariable @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1") @Max(value = Long.MAX_VALUE, message = "Invalid ID: Id must be less than or equal to 9 223 372 036 854 775 807") Long userId) {
        favoriteService.insertFavorite(favoriteRequestDto, userId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteFavoriteByProductId(
            @RequestParam("userId") @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1") @Max(value = Long.MAX_VALUE, message = "Invalid ID: Id must be less than or equal to 9 223 372 036 854 775 807") Long userId,

            @RequestParam("productId") @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1") @Max(value = Long.MAX_VALUE, message = "Invalid ID: Id must be less than or equal to 9 223 372 036 854 775 807") Long productId) {
        favoriteService.deleteFavoriteByProductId(userId, productId);
    }
}

