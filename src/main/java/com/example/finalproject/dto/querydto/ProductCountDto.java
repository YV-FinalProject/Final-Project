package com.example.finalproject.dto.querydto;

import lombok.*;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCountDto {
    private Long productId;
    private String name;
    private String status;
    private Integer count;
    private BigDecimal sum;

}
