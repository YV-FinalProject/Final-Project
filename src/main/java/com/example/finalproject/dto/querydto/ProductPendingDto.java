package com.example.finalproject.dto.querydto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPendingDto {

    private Long productId;
    private String name;
    private Integer count;
    private Timestamp createdAt;
}
