package com.example.finalproject.dto.querydto;

import lombok.*;

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
    private String status;
}
