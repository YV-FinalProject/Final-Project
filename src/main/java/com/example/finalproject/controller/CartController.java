package com.example.finalproject.controller;

import com.example.finalproject.dto.requestdto.CartItemRequestDto;
import com.example.finalproject.dto.responsedto.CartItemResponseDto;
import com.example.finalproject.security.jwt.JwtAuthentication;
import com.example.finalproject.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tag(name = "Cart controller", description = "Controller for managing user's cart")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/cart")
@Validated
public class CartController {
    private final CartService cartService;

    @Operation(summary = "Getting user's cart", description = "Provides functionality for getting all products in user's cart")
//    @SecurityRequirement(name = "JWT")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<CartItemResponseDto> getCartItems() {

        final JwtAuthentication jwtInfoToken = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        String email = jwtInfoToken.getUsername();

        return cartService.getCartItems(email);
    }

    @Operation(summary = "Inserting a new item in the cart", description = "Provides functionality for inserting a new product into user's cart")
//    @SecurityRequirement(name = "JWT")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void insertCartItem(@RequestBody @Valid CartItemRequestDto cartItemRequestDto) {

//        final JwtAuthentication jwtInfoToken = (JwtAuthentication)SecurityContextHolder.getContext().getAuthentication();
//        String email = jwtInfoToken.getEmail());
        String email = "torstenbormann@example.com";

        cartService.insertCartItem(cartItemRequestDto, email);
    }

    @Operation(summary = "Deleting an item from the cart", description = "Provides functionality for deleting a product from user's cart")
//    @SecurityRequirement(name = "JWT")
    @DeleteMapping(value = "/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCarItemByProductId(@PathVariable("productId")
                                         @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1")
                                         @Parameter(description = "Product identifier") Long productId) {

//        final JwtAuthentication jwtInfoToken = (JwtAuthentication)SecurityContextHolder.getContext().getAuthentication();
//        String email = jwtInfoToken.getEmail());
        String email = "torstenbormann@example.com";

        cartService.deleteCarItemByProductId(email, productId);
    }

}
