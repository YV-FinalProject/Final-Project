package com.example.finalproject.repository;

import com.example.finalproject.dto.responsedto.ProductResponseDto;
import com.example.finalproject.entity.Product;


import com.example.finalproject.entity.query.ProductCountInterface;
import com.example.finalproject.entity.query.ProductPendingInterface;
import com.example.finalproject.entity.query.ProductProfitInterface;
import com.example.finalproject.entity.query.ProductSortInterface;
import org.springframework.data.domain.Sort;
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


    @Query("SELECT product from Product product " +
            "WHERE (:hasCategory = TRUE OR product.category.categoryId = :category) " +
            "AND product.price BETWEEN :minPrice and :maxPrice " +
            "AND (:hasDiscount = TRUE OR product.discountPrice IS NOT NULL) ")
    List<Product> findProductsByFilter(Boolean hasCategory, Long category, Double minPrice, Double maxPrice, Boolean hasDiscount, Sort sortObject);




@Query (value =
           "SELECT  p.ProductID as productId, p.Name as name, SUM(oi.Quantity) as count, o.CreatedAt "+
           "FROM Products p JOIN OrderItems oi ON p.ProductID = oi.ProductID "+
           "JOIN Orders o ON oi.OrderId = o.OrderID " +
           "where o.Status = 'PENDING_PAYMENT' and o.CreatedAt < Now() - INTERVAL :days DAY  " +
           "GROUP BY p.ProductID, o.CreatedAt "+
           "Order by p.Name", nativeQuery = true)
    List<ProductPendingInterface> findProductPending(Integer days);


    @Query(value =
            "SELECT CASE " +
                    "WHEN :period = 'MONTH' THEN DATE_FORMAT(o.CreatedAt, '%Y-%m') " +
                    "WHEN :period = 'WEEK' THEN DATE_FORMAT(o.CreatedAt, '%Y-%u') " +
                    "ELSE DATE_FORMAT(o.CreatedAt, '%Y-%m-%d') " +
            "END as period, SUM(oi.Quantity * p.Price) as sum " +
            "FROM Products p " +
            "JOIN OrderItems oi ON p.ProductID = oi.ProductID " +
            "JOIN Orders o ON oi.OrderId = o.OrderID " +
            "WHERE o.Status = 'PENDING_PAYMENT' AND o.CreatedAt >= " +
            "CASE " +
                    "WHEN :period = 'MONTH' THEN NOW() - INTERVAL :value MONTH " +
                    "WHEN :period = 'WEEK' THEN NOW() - INTERVAL :value WEEK " +
                    "ELSE NOW() - INTERVAL :value DAY " +
            "END " +
            "GROUP BY CASE " +
                    "WHEN :period = 'MONTH' THEN DATE_FORMAT(o.CreatedAt, '%Y-%m') " +
                    "WHEN :period = 'WEEK' THEN DATE_FORMAT(o.CreatedAt, '%Y-%u') " +
                    "ELSE DATE_FORMAT(o.CreatedAt, '%Y-%m-%d') " +
            "END ",
            nativeQuery = true
    )
    List<ProductProfitInterface> findProffitByPeriod(String period, Integer value);

}