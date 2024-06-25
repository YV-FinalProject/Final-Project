package com.example.finalproject.dto.requestdto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto {

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

    @NotBlank(message = "Invalid image: Empty imageURL")
    @Pattern(regexp = "^https?://([-a-z0-9]{2,256}\\.){1,20}[a-z]{2,4}/[-a-zA-Z0-9_.#?&=%/]*$", message = "Invalid URL")
    private String imageURL;

    @NotBlank(message = "Invalid Category: Empty category")
    private String category;
}
