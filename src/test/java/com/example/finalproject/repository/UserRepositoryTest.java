package com.example.finalproject.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
    }

    @Test
    void existsByEmail() {
        String email = "benjamindietrich@example.com";
        String notExistingEmail = "juliaVladimirov@example.com";

        Boolean existsByEmail = userRepository.existsByEmail(email);
        assertTrue(existsByEmail);

        Boolean doesNotExistsByEmail = userRepository.existsByEmail(notExistingEmail);
        assertFalse(doesNotExistsByEmail);

    }

}