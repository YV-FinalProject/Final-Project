package com.example.finalproject.dto.querydto;
import lombok.*;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductProfitDto {
    private String period;
    private BigDecimal sum;
}
