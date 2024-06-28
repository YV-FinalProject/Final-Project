package com.example.finalproject.entity.query;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ProductTest {

        @Id
        @Column(name = "ProductID")
        private long productID;

        @Column(name = "Name")
        private String name;

}
