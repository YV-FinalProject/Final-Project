package com.example.finalproject.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.*;
import com.example.finalproject.dto.requestdto.FavoriteRequestDto;
import com.example.finalproject.dto.responsedto.FavoriteResponseDto;
import com.example.finalproject.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;

    @GetMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Set<FavoriteResponseDto> getFavoritesByUserId(@PathVariable @Positive(message = "User ID must be a positive number") Long userId) {
        return favoriteService.getFavoritesByUserId(userId);
    }

    @PostMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void insertFavorite(@RequestBody @Valid FavoriteRequestDto favoriteRequestDto,
                               @PathVariable @Positive(message = "User ID must be a positive number") Long userId) {
        favoriteService.insertFavorite(favoriteRequestDto, userId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteFavoriteByProductId(@RequestParam("userId") @Positive(message = "User ID must be a positive number") Long userId,
                                         @RequestParam("productId") @Positive(message = "User ID must be a positive number") Long productId) {
        favoriteService.deleteFavoriteByProductId(userId, productId);
    }
}

