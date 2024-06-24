package com.example.finalproject.dto.responsedto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDto {

    private Long productId;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String imageURL;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("category")
    private CategoryResponseDto categoryResponseDto;
}
