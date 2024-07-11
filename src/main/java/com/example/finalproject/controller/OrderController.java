package com.example.finalproject.controller;

import com.example.finalproject.dto.requestdto.OrderRequestDto;
import com.example.finalproject.dto.responsedto.OrderResponseDto;
import com.example.finalproject.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tag(name="Order controller", description="Описание контролера")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/orders")
@Validated
public class OrderController {
    private final OrderService orderService;

    @GetMapping(value = "/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponseDto getOrderById(@PathVariable @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1") @Max(value = Long.MAX_VALUE, message = "Invalid ID: Id must be less than or equal to 9 223 372 036 854 775 807") Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping(value = "/history/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public Set<OrderResponseDto> getOrderHistoryByUserId(@PathVariable @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1") @Max(value = Long.MAX_VALUE, message = "Invalid ID: Id must be less than or equal to 9 223 372 036 854 775 807") Long orderId) {
        return orderService.getOrderHistoryByUserId(orderId);
    }

    @PostMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void insertOrder(@RequestBody @Valid OrderRequestDto orderRequestDto,
                            @PathVariable @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1") @Max(value = Long.MAX_VALUE, message = "Invalid ID: Id must be less than or equal to 9 223 372 036 854 775 807") Long userId) {
        orderService.insertOrder(orderRequestDto, userId);
    }

    @PutMapping(value = "/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public void cancelOrder(@PathVariable @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1") @Max(value = Long.MAX_VALUE, message = "Invalid ID: Id must be less than or equal to 9 223 372 036 854 775 807") Long orderId){
        orderService.cancelOrder(orderId);
    }
}
