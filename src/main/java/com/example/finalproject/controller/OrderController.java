package com.example.finalproject.controller;

import com.example.finalproject.dto.requestdto.OrderRequestDto;
import com.example.finalproject.dto.responsedto.OrderResponseDto;
import com.example.finalproject.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/orders")
@Validated
public class OrderController {
    private final OrderService orderService;

    @GetMapping(value = "/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponseDto getOrderById(@PathVariable @Positive(message = "User ID must be a positive number") Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping(value = "/history/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public Set<OrderResponseDto> getOrderHistoryByUserId(@PathVariable @Positive(message = "User ID must be a positive number") Long orderId) {
        return orderService.getOrderHistoryByUserId(orderId);
    }

    @PostMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void insertOrder(@RequestBody @Valid OrderRequestDto orderRequestDto,
                            @PathVariable @Positive(message = "User ID must be a positive number") Long userId) {
        orderService.insertOrder(orderRequestDto, userId);
    }
}
