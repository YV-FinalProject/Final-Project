package com.example.finalproject.entity.query;

import java.math.BigDecimal;
import java.sql.Timestamp;

public interface ProductPendingInterface {
    Long getProductId();
    String getName();
    Integer getCount();
    Timestamp getCreatedAt();
}
