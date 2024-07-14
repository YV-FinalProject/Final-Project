package com.example.finalproject.entity.query;

import java.math.BigDecimal;

public interface ProductCountInterface {
    Long getProductId();
    String getName();
    Integer getCount();
    BigDecimal getSum();
}
