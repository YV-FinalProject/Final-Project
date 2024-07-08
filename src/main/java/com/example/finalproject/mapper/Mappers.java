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

    public FavoriteResponseDto convertToFavoriteResponseDto(Favorite favorite) {
        FavoriteResponseDto favoriteResponseDto = modelMapper.map(favorite, FavoriteResponseDto.class);
        modelMapper.typeMap(Favorite.class, FavoriteResponseDto.class)
        .addMappings(mapper -> mapper.skip(FavoriteResponseDto::setUserResponseDto));
        favoriteResponseDto.setProductResponseDto(convertToProductResponseDto(favorite.getProduct()));
        return favoriteResponseDto;
    }

    public Favorite convertToFavorite(FavoriteResponseDto favoriteDto) {
        return modelMapper.map(favoriteDto, Favorite.class);
    }


    public CartResponseDto convertToCartResponseDto(Cart cart) {
        return modelMapper.map(cart, CartResponseDto.class);
    }

    public Cart convertToCart(CartRequestDto cartRequestDto) {
        return modelMapper.map(cartRequestDto, Cart.class);
    }


    public CartItemResponseDto convertToCartItemResponseDto(CartItem cartItem) {
        modelMapper.typeMap(CartItem.class, CartItemResponseDto.class)
                .addMappings(mapper -> mapper.skip(CartItemResponseDto::setCartResponseDto));
        CartItemResponseDto cartItemResponseDto = modelMapper.map(cartItem, CartItemResponseDto.class);
        cartItemResponseDto.setProductResponseDto(convertToProductResponseDto(cartItem.getProduct()));
        return cartItemResponseDto;
    }

    public CartItem convertToCartItem(CartItemRequestDto cartItemsDto) {
        return modelMapper.map(cartItemsDto, CartItem.class);
    }


    public OrderResponseDto convertToOrderResponseDto(Order order) {
           modelMapper.typeMap(Order.class, OrderResponseDto.class)
            .addMappings(mapper -> mapper.skip(OrderResponseDto::setUserResponseDto));
        return modelMapper.map(order, OrderResponseDto.class);

    }

    public Order convertToOrder(OrderRequestDto ordersRequestDto) {
        return modelMapper.map(ordersRequestDto, Order.class);
    }


    public OrderItemResponseDto convertToOrderItemResponseDto(OrderItem orderItem) {
        modelMapper.typeMap(OrderItem.class, OrderItemResponseDto.class)
                .addMappings(mapper -> mapper.skip(OrderItemResponseDto::setOrderResponseDto));
        OrderItemResponseDto orderItemResponseDto = modelMapper.map(orderItem, OrderItemResponseDto.class);
        orderItemResponseDto.setProductResponseDto(convertToProductResponseDto(orderItem.getProduct()));
        return orderItemResponseDto;
    }

    public OrderItem convertToOrderItem(OrderItemRequestDto orderItemRequestDto) {
        return modelMapper.map(orderItemRequestDto, OrderItem.class);
    }


    public Product convertToProduct(ProductRequestDto productRequestDto) {
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

    public Category convertToCategory(CategoryRequestDto categoryRequestDto) {
        return modelMapper.map(categoryRequestDto, Category.class);
    }


//   modelMapper.typeMap(CartItem.class, CartItemResponseDto.class)
//            .addMappings(mapper -> mapper.skip(CartItemResponseDto::setCartResponseDto));

}