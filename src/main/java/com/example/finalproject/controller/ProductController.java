package com.example.finalproject.controller;

import com.example.finalproject.dto.ProductDto;
import com.example.finalproject.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ProductDto getProductsById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductsById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto insertProduct(@RequestBody ProductDto productsDto) {
        return productService.insertProduct(productsDto);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto updateProducts(@RequestBody ProductDto productsDto,@PathVariable Long id) {
        return productService.updateProduct(productsDto, id);
    }


}
