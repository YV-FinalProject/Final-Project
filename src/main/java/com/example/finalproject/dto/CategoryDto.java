package com.example.finalproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    @NotBlank(message = "Id не должно быть пустым")
    private  Long  id;
    @NotBlank(message = "Имя не должно быть пустым")
    private String name;
}