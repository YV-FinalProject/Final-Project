package com.example.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Builder
public class ProductCountDto {
    private Long productId;
    private String name;
    private Integer count;
    private BigDecimal sum;

    public ProductCountDto(Long productId, String name, Integer count, BigDecimal sum) {
        this.productId = productId;
        this.name = name;
        this.count = count;
        this.sum = sum;
    }
}