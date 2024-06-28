package com.example.finalproject.repository;

import com.example.finalproject.entity.Product;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {


    @Query(value =
            "SELECT p.ProductID, p.Name, SUM(Quantity) Count, SUM(Quantity*Price) Sum " +
                    "FROM Products p JOIN OrderItems oi ON p.ProductID = oi.ProductID " +
                    "JOIN Orders o ON oi.OrderId = o.OrderID " +
                    "WHERE o.Status  = ?1 " +
                    "GROUP BY p.ProductID, p.Name " +
                    "ORDER BY Count DESC  " +
                    "LIMIT 10"
            ,nativeQuery = true
    )
    List findTop10Products(String status);

    @Query(value =
            "SELECT  * FROM Products "+
            "WHERE (?1 OR CategoryID = ?2) "+
            "AND Price between ?3 and ?4 " +
            "AND (?5 OR DiscountPrice IS NOT NULL) " +
            "ORDER BY ?6 ASC "
            ,nativeQuery = true)
    List findProductsByFilter(Boolean isCategory, Long category, Double minPrice, Double maxPrice, Boolean isDiscount, String sort);
}