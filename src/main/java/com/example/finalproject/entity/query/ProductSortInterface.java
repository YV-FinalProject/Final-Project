package com.example.finalproject.entity.query;

import java.math.BigDecimal;
import java.sql.Timestamp;

public interface ProductSortInterface {
    Long getProductID();
    String getName();
    String getDescription();
    BigDecimal getPrice();
    BigDecimal getDiscountPrice();
    Long getCategoryID();
    String getImageURL();
    Timestamp getCreatedAt();
    Timestamp getUpdatedAt();
}
