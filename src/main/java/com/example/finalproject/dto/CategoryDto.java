package com.example.finalproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategoryDto {
    private  Long  id;
    private String name;
    public CategoryDto(long id,  String name){
        this.id = id;
        this.name = name;
    }
}
