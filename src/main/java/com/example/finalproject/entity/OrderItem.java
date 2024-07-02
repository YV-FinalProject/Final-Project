package com.example.finalproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.*;

@Entity
@Table(name = "OrderItems")
@Getter
@Setter
//@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderItemID")
    private Long orderItemID;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "PriceAtPurchase")
    private BigDecimal priceAtPurchase;

    @ManyToOne
    @JoinColumn(name = "OrderID", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;

}
