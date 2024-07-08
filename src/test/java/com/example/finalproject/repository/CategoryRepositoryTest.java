package com.example.finalproject.repository;

import com.example.finalproject.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    TestEntityManager entityManager;


    @BeforeEach
    void setUp() {
    }

    @Test
    void findCategoryByName() {
        String categoryName = "Tools and equipment";
        String wrongCategoryName = "Sales";

        Category foundedCategory = categoryRepository.findCategoryByName(categoryName);
        assertNotNull(foundedCategory);
        assertEquals("Tools and equipment", foundedCategory.getName());

        Category notFoundedCategory = categoryRepository.findCategoryByName(wrongCategoryName);
        assertNull(notFoundedCategory);
    }
}