package com.example.finalproject.service;

import com.example.finalproject.config.MapperUtil;
import com.example.finalproject.dto.requestdto.CartItemRequestDto;
import com.example.finalproject.dto.responsedto.CartItemResponseDto;
import com.example.finalproject.entity.*;
import com.example.finalproject.exception.DataNotFoundInDataBaseException;
import com.example.finalproject.mapper.Mappers;
import com.example.finalproject.repository.CartItemRepository;
import com.example.finalproject.repository.CartRepository;
import com.example.finalproject.repository.ProductRepository;
import com.example.finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    private final Mappers mappers;

    @Transactional
    public Set<CartItemResponseDto> getCartItemsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Set<CartItem> cartItemsSet = user.getCart().getCartItems();
            return MapperUtil.convertSet(cartItemsSet, mappers::convertToCartItemResponseDto);
        } else {
            throw new DataNotFoundInDataBaseException("Data not found in database.");
        }
    }

    @Transactional
    public void insertCartItem(CartItemRequestDto cartItemRequestDto, Long userId) {

        CartItem cartItemToInsert = new CartItem();
        User user = userRepository.findById(userId).orElse(null);
        Product product = productRepository.findById(cartItemRequestDto.getProductId()).orElse(null);
        if (user != null && product != null) {
            Cart cart = cartRepository.findById(user.getCart().getCartId()).orElse(null);
            if (cart == null) {
                Cart cartToInsert = new Cart();
                cartToInsert.setUser(user);
                cartRepository.save(cartToInsert);
                user.setCart(cartToInsert);
                userRepository.save(user);
                cartItemToInsert.setCart(cartToInsert);
            }
            cartItemToInsert.setCart(cart);
            cartItemToInsert.setCartItemId(0L);
            cartItemToInsert.setProduct(product);
            cartItemToInsert.setQuantity(cartItemRequestDto.getQuantity());
            cartItemRepository.save(cartItemToInsert);
        } else {
            throw new DataNotFoundInDataBaseException("Data not found in database.");
        }
    }

    @Transactional
    public void deleteCarItemByProductId(Long userId, Long productId) {
        User user = userRepository.findById(userId).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);
        if (user != null && product != null) {
            Set <CartItem> cartItemSet = user.getCart().getCartItems();
            for(CartItem item : cartItemSet){
                if(item.getProduct().getProductId() == productId){
                    cartItemRepository.delete(item);
                }
            }
        } else {
            throw new DataNotFoundInDataBaseException("Data not found in database.");
        }
    }
}
