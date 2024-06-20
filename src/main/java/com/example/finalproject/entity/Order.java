package com.example.finalproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderID") //
    private long orderId;

    @Column(name = "CreatedAt")
    private Timestamp createdAt; //

    @Column(name = "DeliveryAddress")
    private String deliveryAddress; //

    @Column(name = "ContactPhone")
    private String contactPhone; //

    @Column(name = "DeliveryMethod")
    private String deliveryMethod; //

    @Column(name = "Status")
    @Enumerated(EnumType.STRING)
    private Status status; //

    @Column(name = "UpdatedAt")
    private Timestamp updatedAt; //

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderItems> orderItems = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID") //
    private User user;
}
