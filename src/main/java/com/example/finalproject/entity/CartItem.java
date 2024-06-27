package com.example.finalproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CartItems")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @Column(name = "CartItemID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne
    @JoinColumn(name="CartID", nullable=false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name="ProductID", nullable=false)
    private Product product;

    @Column(name = "Quantity")
    private Integer quantity;
}

