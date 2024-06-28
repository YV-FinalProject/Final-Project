package com.example.finalproject.controller;

import com.example.finalproject.dto.requestdto.ProductRequestDto;
import com.example.finalproject.dto.responsedto.ProductResponseDto;
import com.example.finalproject.entity.Product;
import com.example.finalproject.entity.query.ProductCount;
import com.example.finalproject.entity.query.ProductTest;
import com.example.finalproject.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDto getProductsById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductsById(@PathVariable Long id) {
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
                              @PathVariable @Valid @Min(1) Long id) {
        productService.updateProduct(productRequestDto,id);
    }

//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping
//    public List<ProductResponseDto> getProducts(
//            @RequestParam(value = "category", required = false) Long categoryId,
//            @RequestParam(value = "minPrice", required = false)  Double minPrice,
//            @RequestParam(value = "maxPrice", required = false)  Double maxPrice,
//            @RequestParam(value = "discount", required = false, defaultValue = "false")  Boolean isDiscount,
//            @RequestParam(value = "sort", required = false)  String sort
//    ) {
//        return productService.getProducts(
//                categoryId,
//                minPrice,
//                maxPrice,
//                isDiscount,
//                sort
//        );
//
//    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/top10")
    public List<ProductCount> getTop10Products(@RequestParam(value = "status", required = false) String status) {
        return  productService.getTop10Products(status);
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/test")
    public List<ProductResponseDto> test() {
        return  productService.test();
    }

}
