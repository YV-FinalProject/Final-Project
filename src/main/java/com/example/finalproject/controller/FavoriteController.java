package com.example.finalproject.controller;

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
    public Set<FavoriteResponseDto> getFavoritesByUserId(@PathVariable Long userId) {
        return favoriteService.getFavoritesByUserId(userId);
    }

    @PostMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void insertFavorite(@RequestBody FavoriteRequestDto favoriteRequestDto,
                               @PathVariable Long userId) {
        favoriteService.insertFavorite(favoriteRequestDto, userId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteFavoriteByProductId(@RequestParam("userId") Long userId,
                                         @RequestParam("productId") Long productId) {
        favoriteService.deleteFavoriteByProductId(userId, productId);
    }
}

