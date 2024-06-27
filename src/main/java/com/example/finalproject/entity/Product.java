package com.example.finalproject.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "Products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Product {

    @Id
    @Column(name = "ProductID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "Name")
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "Price")
    private BigDecimal price;

    @Column(name = "DiscountPrice")
    private BigDecimal discountPrice;

    @Column(name = "ImageURL")
    private String imageURL;

    @CreationTimestamp
    @Column(name = "CreatedAt")
    private Timestamp createdAt;

    @CreationTimestamp
    @Column(name = "UpdatedAt")
    private Timestamp updatedAt;

   @ManyToOne
   @JoinColumn(name = "CategoryId")
    private Category category;

}