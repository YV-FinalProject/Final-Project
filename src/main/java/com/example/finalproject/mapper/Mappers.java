package com.example.finalproject.mapper;

import com.example.finalproject.dto.CartDto;
import com.example.finalproject.dto.CartItemDto;
import com.example.finalproject.dto.CategoryDto;
import com.example.finalproject.dto.FavoriteDto;
import com.example.finalproject.entity.Cart;
import com.example.finalproject.entity.CartItem;
import com.example.finalproject.entity.Category;
import com.example.finalproject.entity.Favorite;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mappers {

    private final ModelMapper modelMapper;

//    public UsersDto convertToUsersDto(Users users) {
//        UsersDto usersDto = modelMapper.map(users, UsersDto.class);
//    Разруливаем вручную двухстороннюю связь один-к-одному
//    Cart cart = user.getCart();
//        if (cart!=null) {
//        cart.setUser(null);
//        usersDto.setCartDto(convertToCartDto(cart));
//    }
//        usersDto.setPasswordHash("***"); // замещаем пароль фиктивнім значением
//        return usersDto;
//    }

//    public Users convertToUsers(UsersDto usersDto) {
//        return modelMapper.map(usersDto, Users.class);
//    }

    public FavoriteDto convertToFavoritesDto(Favorite favorites) {
        return modelMapper.map(favorites, FavoriteDto.class);
    }

    public Favorite convertToFavorites(FavoriteDto favoritesDto) {
        return modelMapper.map(favoritesDto, Favorite.class);
    }

    public CartDto convertToCartDto(Cart cart) {
        CartDto cartDto = modelMapper.map(cart, CartDto.class);
//        //Разруливаем вручную двухстороннюю связь один-к-одному
//        Users users = cart.getUser();
//        if(users!=null) {
//            users.setCart(null);
//            cartDto.setUserDto(convertToUsersDto(users));
//        }
        return cartDto;
    }

    public Cart convertToCart(CartDto cartDto) {
        return modelMapper.map(cartDto, Cart.class);
    }

    public CartItemDto convertToCartItemsDto(CartItem cartItems) {
        return modelMapper.map(cartItems, CartItemDto.class);
    }

    public CartItem convertToCartItems(CartItemDto cartItemsDto) {
        return modelMapper.map(cartItemsDto, CartItem.class);
    }

    public CategoryDto convertToCategoryDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }
    public Category convertToCategory(CategoryDto categoryDto){
        return modelMapper.map(categoryDto, Category.class);
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
}