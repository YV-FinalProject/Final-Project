package com.example.finalproject.controller;

import com.example.finalproject.dto.requestdto.CartItemRequestDto;
import com.example.finalproject.dto.responsedto.CartItemResponseDto;
import com.example.finalproject.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tag(name="Cart controller", description="Описание контролера")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/cart")
@Validated
public class CartController {
    private final CartService cartService;

    @GetMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Set<CartItemResponseDto> getCartItemsByUserId(@PathVariable @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1") @Max(value = Long.MAX_VALUE, message = "Invalid ID: Id must be less than or equal to 9 223 372 036 854 775 807") Long userId) {
        return cartService.getCartItemsByUserId(userId);
    }

    @PostMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void insertCartItem(@RequestBody @Valid CartItemRequestDto cartItemRequestDto,
                               @PathVariable @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1") @Max(value = Long.MAX_VALUE, message = "Invalid ID: Id must be less than or equal to 9 223 372 036 854 775 807") Long userId) {
        cartService.insertCartItem(cartItemRequestDto, userId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteCarItemByProductId(@RequestParam("userId") @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1") @Max(value = Long.MAX_VALUE, message = "Invalid ID: Id must be less than or equal to 9 223 372 036 854 775 807") Long userId,
                                         @RequestParam("productId") @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1") @Max(value = Long.MAX_VALUE, message = "Invalid ID: Id must be less than or equal to 9 223 372 036 854 775 807") Long productId) {
        cartService.deleteCarItemByProductId(userId, productId);
    }

}
