package com.example.finalproject.controller;

import com.example.finalproject.dto.requestdto.CartItemRequestDto;
import com.example.finalproject.dto.responsedto.CartItemResponseDto;
import com.example.finalproject.service.CartService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/cart")
public class CartController {
    private final CartService cartService;

    @GetMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Set<CartItemResponseDto> getCartItemsByUserId(@PathVariable @Positive(message = "User ID must be a positive number") Long userId) {
        return cartService.getCartItemsByUserId(userId);
    }

    @PostMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void insertCartItem(@RequestBody CartItemRequestDto cartItemRequestDto,
                               @PathVariable @Positive(message = "User ID must be a positive number") Long userId) {
        cartService.insertCartItem(cartItemRequestDto, userId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteCarItemByProductId(@RequestParam("userId") @Positive(message = "User ID must be a positive number") Long userId,
                                         @RequestParam("productId") @Positive(message = "User ID must be a positive number") Long productId) {
        cartService.deleteCarItemByProductId(userId, productId);
    }

}
