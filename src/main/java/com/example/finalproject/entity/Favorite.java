package com.example.finalproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Favorites")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {

    @Id
    @Column(name = "FavoriteID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favoriteId;

    @ManyToOne
    @JoinColumn(name="UserID", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="ProductID", nullable=false)
    private Product product;

}
