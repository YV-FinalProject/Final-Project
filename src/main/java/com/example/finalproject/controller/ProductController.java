package com.example.finalproject.controller;

import com.example.finalproject.dto.requestdto.ProductRequestDto;
import com.example.finalproject.dto.responsedto.ProductResponseDto;
import com.example.finalproject.entity.query.ProductCountInterface;
import com.example.finalproject.entity.query.ProductPendingInterface;
import com.example.finalproject.entity.query.ProductProfitInterface;
import com.example.finalproject.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Tag(name="Product controller", description="Описание контролера")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/products")
@Validated
public class ProductController {
    private final ProductService productService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDto getProductsById(@PathVariable @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1") @Max(value = Long.MAX_VALUE, message = "Invalid ID: Id must be less than or equal to 9 223 372 036 854 775 807") Long id) {
        return productService.getProductById(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductsById(@PathVariable @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1") @Max(value = Long.MAX_VALUE, message = "Invalid ID: Id must be less than or equal to 9 223 372 036 854 775 807") Long id) {
        productService.deleteProductById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insertProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {
        productService.insertProduct(productRequestDto);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@RequestBody @Valid ProductRequestDto productRequestDto,
                              @PathVariable @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1") @Max(value = Long.MAX_VALUE, message = "Invalid ID: Id must be less than or equal to 9 223 372 036 854 775 807") Long id) {
        productService.updateProduct(productRequestDto, id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void setDiscountPrice(@RequestParam("id") @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1") @Max(value = Long.MAX_VALUE, message = "Invalid ID: Id must be less than or equal to 9 223 372 036 854 775 807") Long id,
                                 @RequestParam("discountPrice") @DecimalMin(value = "0.0") @Digits(integer=6, fraction=2) BigDecimal discountPrice){
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
        @RequestParam(value = "category", required = false) @Min(value = 1, message = "Invalid ID: Id must be greater than or equal to 1") @Max(value = Long.MAX_VALUE, message = "Invalid ID: Id must be less than or equal to 9 223 372 036 854 775 807") Long categoryId,
        @RequestParam(value = "minPrice", required = false)  @DecimalMin(value = "0.0") @Digits(integer=6, fraction=2) BigDecimal minPrice,
        @RequestParam(value = "maxPrice", required = false)  @DecimalMax (value = "999999.0") @Digits(integer=6, fraction=2) BigDecimal maxPrice,
        @RequestParam(value = "discount", required = false, defaultValue = "false")  @NotNull(message = "This parameter can not be null, enter true or false.") Boolean hasDiscount,
        @RequestParam(value = "sort", required = false)  @NotEmpty(message = "Sorting parameters must be specified, for example: name,asc or price,desc.") String[] sort) {

    return productService.findProductsByFilter(categoryId, minPrice, maxPrice, hasDiscount, sort);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/top10")
    public List<ProductCountInterface> getTop10Products(@RequestParam(value = "status", required = false) @NotBlank(message = "Status of order cannot be blank, enter PAID or CANCELED.") String status) {
        return  productService.getTop10Products(status);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/pending")
    public List<ProductPendingInterface> getProductPending(@RequestParam(value = "day", required = false)  @Positive(message = "Number of days must be a positive number.") Integer day) {
        return  productService.findProductPending(day);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/profit")
    public List<ProductProfitInterface> getProfitByPeriod(
            @RequestParam(value = "type", required = false) @NotBlank(message = "This parameter cannot be blank, enter DAY, WEEK or YEAR") String type,
            @RequestParam(value = "period", required = false) @Positive(message = "Period must be a positive number.") Integer period) {
        return  productService.findProductProfit(type, period);
    }
}
