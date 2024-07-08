package com.example.finalproject.repository;

import com.example.finalproject.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    TestEntityManager entityManager;

    @BeforeEach
    void setUp() {

    }

    @Test
    void deleteById() {
        Long productId = 5L;

        productRepository.deleteById(productId);

        Product deletedOrder = productRepository.findById(productId).orElse(null);
        assertNull(deletedOrder);
    }

    @Test
    void getMaxDiscountProduct() {

        List<Product> productList = productRepository.getMaxDiscountProduct();

        assertNotNull(productList);
        assertEquals(1, productList.size());
        assertEquals(BigDecimal.valueOf(2.99) , productList.getFirst().getDiscountPrice());
    }

//    @Test
//    void findTop10Products(){
//
//        String status = "PAID";
//
//        List<String> top10Products =  productRepository.findTop10Products(status);
//        assertNotNull(top10Products);
//        assertEquals(10, top10Products.size());
//        System.out.println(top10Products.getFirst());
//    }

}