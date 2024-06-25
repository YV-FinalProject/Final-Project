package com.example.finalproject.dto.responsedto;

import com.example.finalproject.entity.enums.*;
import com.fasterxml.jackson.annotation.*;
import lombok.*;
import java.sql.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {

    private Long orderId;
    private Timestamp createdAt;
    private String deliveryAddress;
    private String contactPhone;
    private DeliveryMethod deliveryMethod;
    private Status status;
    private Timestamp updatedAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("user")
    private UserResponseDto userResponseDto;
}

