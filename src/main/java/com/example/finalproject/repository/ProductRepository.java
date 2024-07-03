package com.example.finalproject.repository;

import com.example.finalproject.entity.Product;


import com.example.finalproject.entity.query.ProductCountInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query(value =
            "SELECT  p.ProductID as productId, p.Name as name, SUM(Quantity) as count, SUM(Quantity*Price) as sum " +
                    "FROM Products p JOIN OrderItems oi ON p.ProductID = oi.ProductID " +
                    "JOIN Orders o ON oi.OrderId = o.OrderID " +
                    "WHERE o.Status  = ?1 " +
                    "GROUP BY p.ProductID, p.Name " +
                    "ORDER BY Count DESC  " +
                    "LIMIT 10"
            , nativeQuery = true
    )
    List<ProductCountInterface> findTop10Products(String status);


    @Query(value =
            "SELECT  ProductID, Name, Description, Price, DiscountPrice, CategoryID, ImageURL, CreatedAt, UpdatedAt FROM Products "+
            "WHERE (?1 OR CategoryID = ?2) "+
            "AND Price between ?3 and ?4 " +
            "AND (?5 OR DiscountPrice IS NOT NULL) " +
            "ORDER BY ?6 ASC "
            ,nativeQuery = true)
    List<Product> findProductsByFilter(Boolean isCategory, Long category, Double minPrice, Double maxPrice, Boolean isDiscount, String sort);
}