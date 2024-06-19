package com.example.finalproject.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Products")
public class ProductEntity {
    @Id
    private Long productID;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageURL;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    private BigDecimal discountPrice;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryID")
    private CategoryEntity category;
}
