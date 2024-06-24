package com.example.finalproject.mapper;

import com.example.finalproject.dto.*;
import com.example.finalproject.entity.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mappers {

    private final ModelMapper modelMapper;

    public UserResponseDto convertToUserResponseDto(User user) {
        modelMapper.typeMap(User.class, UserResponseDto.class)
                .addMappings(mapper -> mapper.skip(UserResponseDto::setCartDto));
        modelMapper.typeMap(User.class, UserResponseDto.class)
                .addMappings(mapper -> mapper.skip(UserResponseDto::setOrdersDto));
        modelMapper.typeMap(User.class, UserResponseDto.class)
                .addMappings(mapper -> mapper.skip(UserResponseDto::setFavoritesDto));

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

    public Favorite convertToFavorites(FavoriteResponseDto favoritesDto) {
        return modelMapper.map(favoritesDto, Favorite.class);
    }

    public CartResponseDto convertToCartRequestDto(Cart cart) {
        CartResponseDto cartDto = modelMapper.map(cart, CartResponseDto.class);
//        //Разруливаем вручную двухстороннюю связь один-к-одному
//        Users users = cart.getUser();
//        if(users!=null) {
//            users.setCart(null);
//            cartDto.setUserDto(convertToUsersDto(users));
//        }
        return cartDto;
    }

    public Cart convertToCart(CartResponseDto cartDto) {
        return modelMapper.map(cartDto, Cart.class);
    }

    public CartItemRequestDto convertToCartItemRequestDto(CartItem cartItems) {
        return modelMapper.map(cartItems, CartItemRequestDto.class);
    }

    public CartItem convertToCartItems(CartItemRequestDto cartItemsDto) {
        return modelMapper.map(cartItemsDto, CartItem.class);
    }

//    public OrdersDto convertToOrdersDto(Orders orders) {
//        return modelMapper.map(orders, OrdersDto.class);
//    }
//
//    public Orders convertToOrders(OrdersDto ordersDto) {
//        return modelMapper.map(ordersDto, Orders.class);
//    }
//
//    public OrderItemsDto convertToOrderItemsDto(OrderItems orderItems) {
//        return modelMapper.map(orderItems, OrderItemsDto.class);
//    }
//
//    public OrderItems convertToOrderItems(OrderItemsDto orderItemsDto) {
//        return modelMapper.map(orderItemsDto, OrderItems.class);
//    }



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