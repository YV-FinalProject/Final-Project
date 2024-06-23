package com.example.finalproject.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDto {

    @Id
    @Column(name = "ProductID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotBlank(message = "Invalid name: Empty name")
    private String name;

    @NotBlank(message = "Invalid description: Empty description")
    private String description;

    @DecimalMin(value = "0.0")
    @Digits(integer=4, fraction=2)
    private BigDecimal price;

    @DecimalMin(value = "0.0")
    @Digits(integer=4, fraction=2)
    private BigDecimal discountPrice;

    @CreationTimestamp
    @Column(name = "CreatedAt")
    private Timestamp createdAt;

    @CreationTimestamp
    @Column(name = "UpdatedAt")
    private Timestamp updatedAt;

   @NotBlank(message = "Invalid image: Empty imageURL")
    // @Pattern(regexp = "^(?:[\w]\:|\\)(\\[a-z_\-\s0-9\.]+)+\.(txt|gif|pdf|doc|docx|xls|xlsx)$", message = "Invalid URL")
    private String imageURL;

    private Long CategoryId;


//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonProperty("category")
//    @NotBlank(message = "Invalid Category: Empty Category")
//    private String category;

//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonProperty("category")
//    private CategoriesDto category;


}
