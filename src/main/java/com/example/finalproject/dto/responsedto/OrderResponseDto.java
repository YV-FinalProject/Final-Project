package com.example.finalproject.dto.responsedto;

import com.example.finalproject.dto.requestdto.OrderItemRequestDto;
import com.example.finalproject.entity.enums.*;
import com.fasterxml.jackson.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import java.sql.*;
import java.util.Set;

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

//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonProperty("user")
//    private UserResponseDto userResponseDto;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("items")
    private Set<OrderItemResponseDto> orderItemsSet;
}

