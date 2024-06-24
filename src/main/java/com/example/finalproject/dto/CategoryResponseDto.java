package com.example.finalproject.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponseDto {

    private long categoryId;
    private String name;
}