package com.example.finalproject.repository;

import com.example.finalproject.entity.*;
import org.springframework.data.jpa.repository.*;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
}

