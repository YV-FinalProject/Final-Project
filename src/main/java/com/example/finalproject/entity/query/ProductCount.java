package com.example.finalproject.entity.query;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "Products")
@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter
public class ProductCount {
    @Id
    @Column(name = "ProductID")
    private Long productId;

    @Column(name = "Name")
    private String name;

    @Column(name = "Count")
    private Integer count;

    @Column(name = "Sum")
    private BigDecimal sum;
}