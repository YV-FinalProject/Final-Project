package com.example.finalproject.repository;

import com.example.finalproject.entity.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email=?1")
    List<User> getByEmail(String email);
}

