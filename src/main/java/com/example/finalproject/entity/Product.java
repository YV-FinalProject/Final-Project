package com.example.finalproject.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "CategoryId")
    private Category category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)//убрала cascade = CascadeType.ALL, с ним невозможно удалить связанные объекты, например favorites
    private Set<Favorite> favorites = new HashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)//убрала cascade = CascadeType.ALL, с ним невозможно удалить сами products
    private Set<OrderItem> orderItems = new HashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)//убрала cascade = CascadeType.ALL, с ним невозможно удалить сами products
    private Set<CartItem> cartItems = new HashSet<>();
}
