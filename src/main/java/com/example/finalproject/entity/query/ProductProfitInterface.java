package com.example.finalproject.entity.query;

import java.math.BigDecimal;
import java.sql.Timestamp;

public interface ProductProfitInterface {
    String getPeriod();
    BigDecimal getSum();
}
