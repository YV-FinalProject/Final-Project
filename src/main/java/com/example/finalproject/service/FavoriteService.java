package com.example.finalproject.service;

import com.example.finalproject.config.MapperUtil;
import com.example.finalproject.dto.requestdto.FavoriteRequestDto;
import com.example.finalproject.dto.responsedto.FavoriteResponseDto;
import com.example.finalproject.entity.*;
import com.example.finalproject.exception.DataAlreadyExistsException;
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

    public Set<FavoriteResponseDto> getFavorites(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            Set<Favorite> favoritesList = user.getFavorites();
            return MapperUtil.convertSet(favoritesList, mappers::convertToFavoriteResponseDto);
        } else {
            throw new DataNotFoundInDataBaseException("User not found in database.");
        }
    }

    @Transactional
    public void insertFavorite(FavoriteRequestDto favoriteRequestDto, String email) {
        Favorite favorite = new Favorite();
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            Product product = productRepository.findById(favoriteRequestDto.getProductId()).orElse(null);
            if (product != null) {
                Set<Favorite> favoriteSet = user.getFavorites();
                for (Favorite item : favoriteSet) {
                    if (item.getProduct().getProductId().equals(favoriteRequestDto.getProductId())) {
                        throw new DataAlreadyExistsException("This product is already in favorites.");
                    }
                }
                favorite.setProduct(product);
                favorite.setUser(user);
                favoriteRepository.save(favorite);
                favoriteSet.add(favorite);

            } else {
                throw new DataNotFoundInDataBaseException("Product not found in database.");
            }
        } else {
            throw new DataNotFoundInDataBaseException("User not found in database.");
        }

    }

    @Transactional
    public void deleteFavoriteByProductId(String email, Long productId) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            Product product = productRepository.findById(productId).orElse(null);
            if (product != null) {
                Set<Favorite> favoritesSet = user.getFavorites();
                for (Favorite item : favoritesSet) {
                    if (item.getProduct().getProductId().equals(productId)) {
                        favoriteRepository.deleteById(item.getFavoriteId());
                    }else {
                        throw new DataNotFoundInDataBaseException("Product not found in Favorites.");
                    }
                }
            } else {
                throw new DataNotFoundInDataBaseException("Product not found in database.");
            }
        } else {
            throw new DataNotFoundInDataBaseException("User not found in database.");
        }

    }
}
