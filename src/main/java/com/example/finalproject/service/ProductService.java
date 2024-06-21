package com.example.finalproject.service;


import com.example.finalproject.config.MapperUtil;
import com.example.finalproject.dto.ProductDto;
import com.example.finalproject.entity.Product;
import com.example.finalproject.exception.DataNotFoundInDataBaseException;
import com.example.finalproject.exception.InvalidValueExeption;
import com.example.finalproject.mapper.Mappers;
import com.example.finalproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final Mappers mappers;

    public ProductDto getProductById(Long id) {
        ProductDto getProduct = mappers.convertToProductDto(productRepository.findById(id).orElse(null));
        if (getProduct != null) {
                return getProduct;
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

    public ProductDto insertProduct(ProductDto productDto) {
        if (productDto.getCategoryId() != null) {
            Product productToInsert = mappers.convertToProduct(productDto);     //  Добавить поиск категории по названию
            productToInsert.setProductId(0L);
            productToInsert.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            return mappers.convertToProductDto(productRepository.save(productToInsert));
        } else {
            throw new DataNotFoundInDataBaseException("Data not found in database.");          //  подумать какую ошибку присобачить Гав!
        }
    }

    public ProductDto updateProduct(ProductDto productDto, Long id) {
        if (id > 0) {
            Product productToUpdate = productRepository.findById(id).orElse(null);
           if  (productToUpdate != null){
                        productToUpdate.setName(productDto.getName());
                        productToUpdate.setDescription(productDto.getDescription());
                        productToUpdate.setImageURL(productDto.getImageURL());
                        productToUpdate.setPrice(productDto.getPrice());
                        productToUpdate.setCategoryId(productDto.getCategoryId());   // Поменять на нормальное значение Категории
                        productToUpdate.setProductId(id);
                        productToUpdate.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                        return mappers.convertToProductDto(productRepository.save(productToUpdate));
        }
            throw  new DataNotFoundInDataBaseException("Data not found in database.");
        }
        else {
             throw new InvalidValueExeption("The value you entered is not valid."); }
    }

}