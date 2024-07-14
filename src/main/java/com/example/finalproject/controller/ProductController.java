package com.example.finalproject.controller;


import com.example.finalproject.dto.querydto.ProductCountDto;
import com.example.finalproject.dto.querydto.ProductPendingDto;
import com.example.finalproject.dto.querydto.ProductProfitDto;
import com.example.finalproject.dto.requestdto.ProductRequestDto;
import com.example.finalproject.dto.responsedto.ProductResponseDto;
import com.example.finalproject.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Product controller", description = "Controller for managing product catalog")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/products")
@Validated
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    @Operation(summary = "Getting product by id", description = "Provides functionality for getting a product from product catalog")
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDto getProductsById(@PathVariable @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1") @Parameter(description = "Product identifier") Long id) {
        return productService.getProductById(id);
    }

    @Operation(summary = "Deleting product by id", description = "Provides functionality for deleting a product from product catalog")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductsById(@PathVariable @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1") @Parameter(description = "Product identifier") Long id) {
        productService.deleteProductById(id);
    }

    @Operation(summary = "Inserting a new product", description = "Provides functionality for inserting a new product into product catalog")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insertProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {
        productService.insertProduct(productRequestDto);
    }

    @Operation(summary = "Updating product by id", description = "Provides functionality for updating product info")
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@RequestBody @Valid ProductRequestDto productRequestDto,
                              @PathVariable @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1") @Parameter(description = "Product identifier") Long id) {
        productService.updateProduct(productRequestDto, id);
    }

    @Operation(summary = "Setting discount price", description = "Provides functionality for setting discount price for a product")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void setDiscountPrice(@RequestParam("id") @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1") @Parameter(description = "Product identifier") Long id,
                                 @RequestParam("discountPrice") @DecimalMin(value = "0.0") @Digits(integer=6, fraction=2) BigDecimal discountPrice){
        productService.setDiscountPrice(id,discountPrice);
    }

    @Operation(summary = "Getting maximum discount price product", description = "Provides functionality for getting product with maximum discount price")
    @GetMapping(value = "/maxDiscount")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDto getMaxDiscountProduct(){
        return productService.getMaxDiscountProduct();
    }

    @Operation(summary = "Getting products sorted by filter", description = "Provides functionality for filtering products by different field (category, minimal or maximal price, discount, ) and sorting them by name, price or creation date in order of increase or decrease")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ProductResponseDto> getProducts(
        @RequestParam(value = "category", required = false) @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1") @Parameter(description = "Category identifier") Long categoryId,
        @RequestParam(value = "minPrice", required = false)  @DecimalMin(value = "0.0") @Digits(integer=6, fraction=2) @Parameter(description = "Minimal price for the filter range") BigDecimal minPrice,
        @RequestParam(value = "maxPrice", required = false)  @DecimalMax (value = "999999.0") @Digits(integer=6, fraction=2) @Parameter(description = "Maximal price for the filter range") BigDecimal maxPrice,
        @RequestParam(value = "discount", required = false, defaultValue = "false")  @NotNull(message = "This parameter can not be null, enter true or false.") @Parameter(description = "Indication whether a discount is available or not") Boolean hasDiscount,
        @RequestParam(value = "sort", required = false)  @Parameter(description = "Sorting parameters in ascending and descending order: by name (name,asc/name,desc), by price(price,asc/price,desc), by creation date(createdAt,asc/createdAt,desc), by update date (updatedAt,asc/updatedAt,desc), by description (description,asc/description,desc)") String[] sort) {
        //
    return productService.findProductsByFilter(categoryId, minPrice, maxPrice, hasDiscount, sort);
    }

    @Operation(summary = "Getting top-10 products", description = "Provides functionality for getting top-10 most purchased and top-10 most canceled products")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/top10")
    public List<ProductCountDto> getTop10Products(@RequestParam(value = "status", required = false) @Parameter(description = "Status of the order ('PAID' or 'CANCELED') in which the product was placed") String status) {
        return  productService.getTop10Products(status);
    }

    @Operation(summary = "Getting 'pending payment' products", description = "Provides functionality for getting products that are in the status 'pending payment' for more than N days")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/pending")
    public List<ProductPendingDto> getProductPending(@RequestParam(value = "day", required = false) @Parameter(description = "Number of days for 'pending payment' status") Integer day) {
        return  productService.findProductPending(day);
    }

    @Operation(summary = "Getting profit for certain period ", description = "Provides functionality for getting profit for certain period (days, months, years)")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/profit")
    public List<ProductProfitDto> getProffitByPeriod(
            @RequestParam(value = "period", required = false) @Parameter(description = "Type of period (DAY, WEEK or MONTH) for profit calculating") String period,
            @RequestParam(value = "value", required = false) @Parameter(description = "Length of period for profit calculating") Integer value) {
        return  productService.findProductProfit( period, value);
    }
}
