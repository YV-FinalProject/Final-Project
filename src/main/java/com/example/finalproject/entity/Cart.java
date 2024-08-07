package com.example.finalproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "Cart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartID")
    private Long cartId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private Set<CartItem> cartItems = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;
}
