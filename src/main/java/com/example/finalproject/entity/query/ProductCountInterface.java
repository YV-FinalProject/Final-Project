package com.example.finalproject.entity.query;

import java.math.BigDecimal;
import java.sql.Timestamp;

public interface ProductCountInterface {
    Long getProductId();
    String getName();
    Integer getCount();
    BigDecimal getSum();
}
