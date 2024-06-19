package com.example.finalproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Categories")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity {
    @Id
    private Long id;
    private String name;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductEntity> products;
}
