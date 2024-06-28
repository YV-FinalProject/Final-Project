package com.example.finalproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

//@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductTest {

    @Id
    @Column(name = "ProductID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "Name")
    private String name;

//    @Column(name = "Description")
//    private String description;
//
//    @Column(name = "Price")
//    private BigDecimal price;
//
//    @Column(name = "DiscountPrice")
//    private BigDecimal discountPrice;
//
//    @Column(name = "ImageURL")
//    private String imageURL;
//
//    @CreationTimestamp
//    @Column(name = "CreatedAt")
//    private Timestamp createdAt;
//
//    @CreationTimestamp
//    @Column(name = "UpdatedAt")
//    private Timestamp updatedAt;
//
//   @ManyToOne(fetch = FetchType.LAZY)
//   @JoinColumn(name = "CategoryId")
//    private Category category;
//
//    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)//убрала cascade = CascadeType.ALL, с ним невозможно удалить связанные объекты, например favorites
//    private Set<Favorite> favorites = new HashSet<>();
//
//    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)//убрала cascade = CascadeType.ALL, с ним невозможно удалить сами products
//    private Set<OrderItem> orderItems = new HashSet<>();
//
//    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)//убрала cascade = CascadeType.ALL, с ним невозможно удалить сами products
//    private Set<CartItem> cartItems = new HashSet<>();
}