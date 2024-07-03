package com.example.finalproject.service;

import com.example.finalproject.config.MapperUtil;
import com.example.finalproject.dto.requestdto.FavoriteRequestDto;
import com.example.finalproject.dto.responsedto.FavoriteResponseDto;
import com.example.finalproject.entity.*;
import com.example.finalproject.exception.DataNotFoundInDataBaseException;
import com.example.finalproject.mapper.Mappers;
import com.example.finalproject.repository.FavoriteRepository;
import com.example.finalproject.repository.ProductRepository;
import com.example.finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final FavoriteRepository favoriteRepository;
    private final Mappers mappers;

    @Transactional
    public Set<FavoriteResponseDto> getFavoritesByUserId(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Set<Favorite> favoritesList = user.getFavorites();
            return MapperUtil.convertSet(favoritesList, mappers::convertToFavoriteResponseDto);
        } else {
            throw new DataNotFoundInDataBaseException("Data not found in database.");
        }
    }

    @Transactional
    public void insertFavorite (FavoriteRequestDto favoriteRequestDto, Long userId){
        Favorite favorite = new Favorite();
        User user = userRepository.findById(userId).orElse(null);
        Product product = productRepository.findById(favoriteRequestDto.getProductId()).orElse(null);
        if (user != null && product != null) {
            favorite.setProduct(product);
            favorite.setUser(user);
            favoriteRepository.save(favorite);

        } else {
            throw new DataNotFoundInDataBaseException("Data not found in database.");
        }
    }

    @Transactional
    public void deleteFavoriteByProductId(Long userId, Long productId){
        User user = userRepository.findById(userId).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);
        if (user != null && product != null) {
            Set<Favorite> favoritesSet = user.getFavorites();
            for(Favorite item : favoritesSet){
                if(item.getProduct().getProductId() == productId){
                    favoriteRepository.deleteById(item.getFavoriteId());
                }
            }
        } else {
            throw new DataNotFoundInDataBaseException("Data not found in database.");
        }
    }
}
