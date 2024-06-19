package com.example.finalproject.mapper;

import com.example.finalproject.dto.CartDto;
import com.example.finalproject.dto.CartItemsDto;
import com.example.finalproject.dto.FavoritesDto;
import com.example.finalproject.entity.Cart;
import com.example.finalproject.entity.CartItems;
import com.example.finalproject.entity.Favorites;
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

    public FavoritesDto convertToFavoritesDto(Favorites favorites) {
        return modelMapper.map(favorites, FavoritesDto.class);
    }

    public Favorites convertToFavorites(FavoritesDto favoritesDto) {
        return modelMapper.map(favoritesDto, Favorites.class);
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

    public CartItemsDto convertToCartItemsDto(CartItems cartItems) {
        return modelMapper.map(cartItems, CartItemsDto.class);
    }

    public CartItems convertToCartItems(CartItemsDto cartItemsDto) {
        return modelMapper.map(cartItemsDto, CartItems.class);
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