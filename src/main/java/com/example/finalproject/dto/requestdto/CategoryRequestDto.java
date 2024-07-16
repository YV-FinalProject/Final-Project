package com.example.finalproject.dto.requestdto;

import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequestDto {


    @Size(min = 2, max = 50, message = "Invalid category name: Must be of 2 - 50 characters")
    private String name;
}
