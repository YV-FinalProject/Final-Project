package com.example.finalproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class CategoryCreateDto {
    @NotBlank(message = "Имя не должно быть пустым")
    private String name;
    public CategoryCreateDto(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
}
