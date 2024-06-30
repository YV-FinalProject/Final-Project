package com.example.finalproject.controller;

import com.example.finalproject.dto.requestdto.CartItemRequestDto;
import com.example.finalproject.dto.responsedto.CartItemResponseDto;
import com.example.finalproject.service.CartService;
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
    public Set<CartItemResponseDto> getCartItemsByUserId(@PathVariable Long userId) {
        return cartService.getCartItemsByUserId(userId);
    }

    @PostMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void insertCartItem(@RequestBody CartItemRequestDto cartItemRequestDto,
                               @PathVariable Long userId) {
        cartService.insertCartItem(cartItemRequestDto, userId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteCarItemByProductId(@RequestParam("userId") Long userId,
                                         @RequestParam("productId") Long productId) {
        cartService.deleteCarItemByProductId(userId, productId);
    }

}
