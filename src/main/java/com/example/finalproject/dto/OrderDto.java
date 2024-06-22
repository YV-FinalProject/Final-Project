package com.example.finalproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    @Valid
    @NotEmpty(message = "Items list cannot be empty")
    private List<OrderItemDto> items;

    @NotBlank(message = "Delivery address cannot be blank")
    @Size(max = 255, message = "Delivery address must be less than or equal to 255 characters")
    @Pattern(regexp = "^[a-zA-Z0-9.,\\s]+$", message = "Delivery address contains invalid characters")
    private String deliveryAddress;

    @NotBlank(message = "Delivery method cannot be blank")
    @Size(min = 3, max = 30, message = "Invalid Delivery method: Must be of 3 - 30 characters")
    private String deliveryMethod;

    private Long orderId;
    private Timestamp createdAt;
    private String contactPhone;
//    private Status status;
    private Timestamp updatedAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("user")
    private UserDto user;
}
