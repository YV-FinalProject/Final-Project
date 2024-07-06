package com.example.finalproject.repository;

import com.example.finalproject.entity.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    @Modifying
    @Query("DELETE FROM Product product " +
            "WHERE product.productId = :id")
    void deleteById(Long id);


    @Query("SELECT product FROM Product product " +
            "WHERE product.discountPrice IS NOT NULL " +
            "AND (SELECT MAX(product.price / product.discountPrice) " +
            "FROM Product product) = (product.price / product.discountPrice)")
    List<Product> getMaxDiscountProduct();


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
    List<String> findTop10Products(String status);



//    @Query("SELECT product from Product product " +
//            "WHERE (:hasCategory = TRUE OR product.category.categoryId = :category) " +
//            "AND product.price BETWEEN :minPrice and :maxPrice " +
//            "AND (:hasDiscount = TRUE OR product.discountPrice IS NOT NULL) ")
//    List<Product> findProductsByFilter(Boolean hasCategory, Long category, Double minPrice, Double maxPrice, Boolean hasDiscount, Sort sortObject);



}