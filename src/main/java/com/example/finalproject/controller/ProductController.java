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
                                 @RequestParam("discountPrice") @DecimalMin(value = "0.0") @Digits(integer=6, fraction=2) @Parameter(description = "Discount price for the product") BigDecimal discountPrice){
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
        @RequestParam(value = "discount", required = false, defaultValue = "false")  @NotNull(message = "This parameter can not be null, enter true or false.") @Parameter(description = "Indicator whether a discount is available or not") Boolean hasDiscount,
        @RequestParam(value = "sort", required = false) @Pattern(regexp = "^((name|price|createdAt|updatedAt)(,asc|,desc)?)?$", message = "Invalid Sorting definition: must be in form '<sort parameter>,<sort order>'") @Parameter(description = "Sorting parameters in ascending and descending order by:<br>name: <code>name,asc</code> / <code>name,desc</code><br>price: <code>price,asc</code> / <code>price,desc</code><br>creation date: <code>createdAt,asc</code> / <code>createdAt,desc</code><br>update date: <code>updatedAt,asc</code> / <code>updatedAt,desc</code>") String[] sort) {
    return productService.findProductsByFilter(categoryId, minPrice, maxPrice, hasDiscount, sort);
    }

    @Operation(summary = "Getting top-10 products", description = "Provides functionality for getting top-10 most purchased and top-10 most canceled products")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/top10")
    public List<ProductCountDto> getTop10Products(@RequestParam(value = "status", required = false) @Pattern(regexp = "^(PAID|CANCELED)$", message = "Invalid order status: Must be PAID or CANCELED") @Parameter(description = "Status of the order in which the product was placed: <code>PAID</code> or <code>CANCELED</code>") String status) {
        return  productService.getTop10Products(status);
    }

    @Operation(summary = "Getting 'pending payment' products", description = "Provides functionality for getting products that are in the status 'pending payment' for more than N days")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/pending")
    public List<ProductPendingDto> getProductPending(@RequestParam(value = "days", required = false) @Positive(message = "Number of days must be a positive number") @Parameter(description = "Number of days for <code>PENDING_PAYMENT</code> status") Integer days) {
        return  productService.findProductPending(days);
    }

    @Operation(summary = "Getting profit for certain period ", description = "Provides functionality for getting profit for certain period (days, months, years)")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/profit")
    public List<ProductProfitDto> getProfitByPeriod(
            @RequestParam(value = "period", required = false) @Pattern(regexp = "^(WEEK|DAY|MONTH)$", message = "Invalid type of period: Must be DAY, WEEK or MONTH") @Parameter(description = "Type of period for profit calculating: <code>DAY</code>, <code>WEEK</code> or <code>MONTH</code>") String period,
            @RequestParam(value = "value", required = false) @Positive(message = "Period length must be a positive number") @Parameter(description = "Length of period for profit calculating") Integer value) {
        return  productService.findProductProfit( period, value);
    }
}
