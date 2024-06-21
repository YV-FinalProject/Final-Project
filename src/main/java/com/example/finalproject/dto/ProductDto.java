package com.example.finalproject.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    @NotBlank(message = "Invalid name: Empty name")
    private String name;
    @NotBlank(message = "Invalid description: Empty description")
    private String description;
    @Pattern(regexp = "^[^0]\\d{1,18}$", message = "Invalid price: not a number")
    private BigDecimal price;
    @NotBlank(message = "Invalid image: Empty imageURL")
    private String imageURL;

    @NotBlank(message = "Invalid Category: Empty Category")
    @Pattern(regexp = "^[^0]\\d{1,18}$", message = "Invalid CategoryId: not a number")

//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonProperty("category")
//    private CategoriesDto category;

    private Long CategoryId;
}
