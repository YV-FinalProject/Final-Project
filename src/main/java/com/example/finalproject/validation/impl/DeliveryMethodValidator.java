package com.example.finalproject.validation.impl;

import com.example.finalproject.entity.enums.*;
import com.example.finalproject.validation.annotation.*;
import jakarta.validation.*;

import java.util.*;

public class DeliveryMethodValidator implements ConstraintValidator<ValidDeliveryMethod, String> {

    @Override
    public void initialize(ValidDeliveryMethod constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return Arrays.stream(DeliveryMethod.values())
                .anyMatch(enumValue -> enumValue.name().equals(value));
    }
}