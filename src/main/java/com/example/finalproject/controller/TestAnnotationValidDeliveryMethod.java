package com.example.finalproject.controller;

import com.example.finalproject.dto.requestdto.*;
import jakarta.validation.*;
import org.springframework.http.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class TestAnnotationValidDeliveryMethod {

    @PostMapping("/orders")
    public ResponseEntity<String> validateStatus(@Valid @RequestBody OrderRequestDto requestDto) {
        return new ResponseEntity<>("Valid status: " + requestDto.getDeliveryMethod(), HttpStatus.OK);
    }
}