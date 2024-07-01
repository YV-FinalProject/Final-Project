package com.example.finalproject.validation.annotation;

import com.example.finalproject.validation.impl.*;
import jakarta.validation.*;

import java.lang.annotation.*;

@Constraint(validatedBy = DeliveryMethodValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDeliveryMethod {
    String message() default "Invalid Delivery method";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}


