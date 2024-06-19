package com.example.finalproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Cart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @Column(name = "CartID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

//    @OneToMany(mappedBy = "Cart", cascade = CascadeType.ALL)
//    private Set<CartItems> cartItems = new HashSet<>();

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "UserID", nullable=false, referencedColumnName = "UserID")
//    private Users users;

}
