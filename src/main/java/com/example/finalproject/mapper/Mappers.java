package com.example.finalproject.mapper;

import com.example.finalproject.dto.requestdto.*;
import com.example.finalproject.dto.responsedto.*;
import com.example.finalproject.entity.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mappers {

    private final ModelMapper modelMapper;

    public UserResponseDto convertToUserResponseDto(User user) {
        UserResponseDto usersResponseDto = modelMapper.map(user, UserResponseDto.class);
        usersResponseDto.setPassword("***");
        return usersResponseDto;
    }

    public User convertToUser(UserRequestDto userRequestDto) {
        return modelMapper.map(userRequestDto, User.class);
    }



    public FavoriteResponseDto convertToFavoriteResponseDto(Favorite favorites) {
        return modelMapper.map(favorites, FavoriteResponseDto.class);
    }

    public Favorite convertToFavorite(FavoriteResponseDto favoritesDto) {
        return modelMapper.map(favoritesDto, Favorite.class);
    }



    public CartResponseDto convertToCartResponseDto(Cart cart) {
        return modelMapper.map(cart, CartResponseDto.class);
    }

    public Cart convertToCart(CartRequestDto cartRequestDto) {
        return modelMapper.map(cartRequestDto, Cart.class);
    }



    public CartItemResponseDto convertToCartItemResponseDto(CartItem cartItem) {
        return modelMapper.map(cartItem, CartItemResponseDto.class);
    }

    public CartItem convertToCartItem(CartItemRequestDto cartItemsDto) {
        return modelMapper.map(cartItemsDto, CartItem.class);
    }


    public OrderResponseDto convertToOrderResponseDto(Order order) {
        return modelMapper.map(order, OrderResponseDto.class);
    }

    public Order convertToOrder(OrderRequestDto ordersRequestDto) {
        return modelMapper.map(ordersRequestDto, Order.class);
    }


    public OrderItemResponseDto convertToOrderItemResponseDto(OrderItem orderItem) {
        return modelMapper.map(orderItem, OrderItemResponseDto.class);
    }

    public OrderItem convertToOrderItem(OrderItemRequestDto orderItemRequestDto) {
        return modelMapper.map(orderItemRequestDto, OrderItem.class);
    }


    public Product convertToProductRequest(ProductRequestDto productRequestDto) {
        return modelMapper.map(productRequestDto, Product.class);
    }

    public ProductResponseDto convertToProductResponseDto(Product product) {
        modelMapper.typeMap(Product.class, ProductResponseDto.class)
                .addMappings(mapper -> mapper.skip(ProductResponseDto::setCategoryResponseDto));
        return modelMapper.map(product, ProductResponseDto.class);
    }


    public CategoryResponseDto convertToCategoryResponseDto(Category category) {
        return modelMapper.map(category, CategoryResponseDto.class);
    }

    public Category convertToCategory(CategoryRequestDto categoriyRequestDto) {
        return modelMapper.map(categoriyRequestDto, Category.class);
    }
}