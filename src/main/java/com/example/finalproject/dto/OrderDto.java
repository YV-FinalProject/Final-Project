package com.example.finalproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    private Long orderId;

    private Timestamp createdAt;

    private String deliveryAddress;

    private String contactPhone;

    private String deliveryMethod;

    private Status status;

    private Timestamp updatedAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("user")
    private UserDto users;
}

