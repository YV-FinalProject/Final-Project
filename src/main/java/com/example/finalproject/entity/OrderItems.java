package com.example.finalproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "Oders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderItemID")
    private Long orderItemID;

    @Column(name = "Quantity")
    private Long quantity;

    @Column(name = "PriceAtPurchase")
    private BigDecimal priceAtPurchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderID")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductID")
    private Product product;
}
