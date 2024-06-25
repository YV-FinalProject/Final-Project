package com.example.finalproject.dto.responsedto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponseDto {

    private long categoryId;
    private String name;
}