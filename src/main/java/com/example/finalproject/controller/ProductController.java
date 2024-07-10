package com.example.finalproject.controller;


import com.example.finalproject.dto.querydto.ProductCountDto;
import com.example.finalproject.dto.querydto.ProductPendingDto;
import com.example.finalproject.dto.querydto.ProductProfitDto;
import com.example.finalproject.dto.requestdto.ProductRequestDto;
import com.example.finalproject.dto.responsedto.ProductResponseDto;
import com.example.finalproject.entity.query.ProductCountInterface;
import com.example.finalproject.entity.query.ProductPendingInterface;
import com.example.finalproject.entity.query.ProductProfitInterface;
import com.example.finalproject.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/products")
@Validated
public class ProductController {
    private final ProductService productService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDto getProductsById(@PathVariable @Positive(message = "Product ID must be a positive number") Long id) {
        return productService.getProductById(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductsById(@PathVariable @Positive(message = "Product ID must be a positive number") Long id) {
        productService.deleteProductById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insertProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {
        productService.insertProduct(productRequestDto);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Validated
    public void updateProduct(@RequestBody @Valid ProductRequestDto productRequestDto,
                              @PathVariable @Positive(message = "Product ID must be a positive number") Long id) {
        productService.updateProduct(productRequestDto, id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Validated
    public void setDiscountPrice(@RequestParam("id") @Positive(message = "Product ID must be a positive number") Long id,
                                 @RequestParam("discountPrice") @DecimalMin(value = "0.0") @Digits(integer=4, fraction=2) BigDecimal discountPrice){
        productService.setDiscountPrice(id,discountPrice);
    }

    @GetMapping(value = "/maxDiscount")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDto getMaxDiscountProduct(){
        return productService.getMaxDiscountProduct();
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ProductResponseDto> getProducts(
        @RequestParam(value = "category", required = false) Long categoryId,
        @RequestParam(value = "minPrice", required = false)  BigDecimal minPrice,
        @RequestParam(value = "maxPrice", required = false)  BigDecimal maxPrice,
        @RequestParam(value = "discount", required = false, defaultValue = "false")  Boolean hasDiscount,
        @RequestParam(value = "sort", required = false)  String[] sort) {

    return productService.findProductsByFilter(categoryId, minPrice, maxPrice, hasDiscount, sort);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/top10")
    public List<ProductCountDto> getTop10Products(@RequestParam(value = "status", required = false) String status) {
        return  productService.getTop10Products(status);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/pending")
    public List<ProductPendingDto> getProductPending(@RequestParam(value = "day", required = false) Integer day) {
        return  productService.findProductPending(day);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/profit")
    public List<ProductProfitDto> getProffitByPeriod(
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "period", required = false)  Integer period) {
        return  productService.findProductProfit( type, period);
    }
}
