package com.example.finalproject.repository;

import com.example.finalproject.entity.Product;
import com.example.finalproject.entity.query.ProductCountInterface;
import com.example.finalproject.entity.query.ProductPendingInterface;
import com.example.finalproject.entity.query.ProductProfitInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;

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

    @Test
    void findTop10Products(){

        String status = "PAID";
        List<ProductCountInterface> top10Products =  productRepository.findTop10Products(status);
        assertNotNull(top10Products);
        assertEquals(10, top10Products.size());
    }

    @Test
    void findProductsByFilter() {
        Boolean hasCategory = true;
        Long categoryId = 1L;
        BigDecimal minPrice = BigDecimal.valueOf(0.00);
        BigDecimal maxPrice = BigDecimal.valueOf(100.00);
        Boolean hasDiscount = true;
        Sort sortObject = orderBy("name", true);
        List<Product> sortedProduct = productRepository.findProductsByFilter(hasCategory,categoryId,minPrice,maxPrice,hasDiscount,sortObject);
        assertNotNull(sortedProduct);
    }

    @Test
    void findProductPending() {
        Integer days = 55;
        List<ProductPendingInterface> productPendingList = productRepository.findProductPending(days);
        assertNotNull(productPendingList);
    }

    @Test
    void findProffitByPeriod() {
        String period ="WEEK";
        Integer value = 12;
        List<ProductProfitInterface>  productProfitList = productRepository.findProffitByPeriod(period, value);
        assertNotNull(productProfitList);
    }

    private Sort orderBy(String sort, Boolean ascending) {
        if (!ascending) {
            return Sort.by(Sort.Direction.DESC, sort);
        } else {
            return Sort.by(Sort.Direction.ASC, sort);
        }
    }
}