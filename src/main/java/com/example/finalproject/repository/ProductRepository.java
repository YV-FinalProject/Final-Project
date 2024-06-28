package com.example.finalproject.repository;

import com.example.finalproject.entity.Product;
import com.example.finalproject.entity.query.ProductCount;

import com.example.finalproject.entity.query.ProductTest;
import org.springframework.data.jpa.repository.JpaRepository;
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
                    "WHERE o.Status  != ?1 " +
                    "GROUP BY p.ProductID, p.Name " +
                    "ORDER BY Count DESC  " +
                    "LIMIT 10"
            ,nativeQuery = true
    )
    List findTop10Products(String status);

    @Query(value=
        "SELECT e FROM  Product e "
            , nativeQuery = false)
        List<Product> testSelect();


    @Query(value=
            "SELECT Products.ProductID, Products.Name FROM  Products "
            , nativeQuery = true)
    List testSelectNative();


}