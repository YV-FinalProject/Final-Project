package com.example.finalproject.service;


import com.example.finalproject.dto.ProductRequestDto;
import com.example.finalproject.dto.ProductResponseDto;
import com.example.finalproject.entity.Product;
import com.example.finalproject.exception.DataNotFoundInDataBaseException;
import com.example.finalproject.exception.InvalidValueExeption;
import com.example.finalproject.mapper.Mappers;
import com.example.finalproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final Mappers mappers;

    public ProductResponseDto getProductById(Long id) {
            Product productResponse = productRepository.findById(id).orElse(null);
            if (productResponse != null) {
                    ProductResponseDto getProductResponseDto = mappers.convertToProductResponseDto(productResponse);
                    return getProductResponseDto;
            } else {
            throw new DataNotFoundInDataBaseException("Data not found in database.");
            }
    }

    public void deleteProductById(Long id) {
        if (productRepository.findById(id).isPresent()) {
            productRepository.findById(id).ifPresent(productRepository::delete);
        } else {
            throw new DataNotFoundInDataBaseException("Data not found in database.");
        }
    }

    public void insertProduct(ProductRequestDto productRequestDto) {
        if (productRequestDto.getCategoryId() != null) {
            Product productToInsert = mappers.convertToProductRequest(productRequestDto);     //  Добавить поиск категории по названию
            productToInsert.setProductId(0L);
            productToInsert.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            productRepository.save(productToInsert);
        } else {
            throw new DataNotFoundInDataBaseException("Data not found in database.");          //  подумать какую ошибку присобачить Гав!
        }
    }

    public void updateProduct(ProductRequestDto productRequestDto, Long id) {
        if (id > 0) {
            Product productToUpdate = productRepository.findById(id).orElse(null);
           if  (productToUpdate != null){
                        productToUpdate.setName(productRequestDto.getName());
                        productToUpdate.setDescription(productRequestDto.getDescription());
                        productToUpdate.setImageURL(productRequestDto.getImageURL());
                        productToUpdate.setPrice(productRequestDto.getPrice());
                        productToUpdate.setDiscountPrice(productRequestDto.getDiscountPrice());
                        productToUpdate.setCategoryId(productRequestDto.getCategoryId());   // Поменять на нормальное значение Категории
                        productToUpdate.setProductId(id);
                        productToUpdate.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                        productRepository.save(productToUpdate);
        }
        else {
             throw new InvalidValueExeption("The value you entered is not valid."); }
    }}
}