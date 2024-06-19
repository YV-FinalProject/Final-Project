package com.example.finalproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CartItems")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItems {

    @Id
    @Column(name = "CartItemID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CartID", nullable=false)
    private Cart cart;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="ProductID", nullable=false)
//    private Products products;

    @Column(name = "Quantity")
    private Integer quantity;
}

