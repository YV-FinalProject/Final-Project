package com.example.finalproject.mapper;

import com.example.finalproject.dto.querydto.ProductCountDto;
import com.example.finalproject.dto.querydto.ProductPendingDto;
import com.example.finalproject.dto.querydto.ProductProfitDto;
import com.example.finalproject.dto.requestdto.*;
import com.example.finalproject.dto.responsedto.*;
import com.example.finalproject.entity.*;
import com.example.finalproject.entity.query.ProductCountInterface;
import com.example.finalproject.entity.query.ProductPendingInterface;
import com.example.finalproject.entity.query.ProductProfitInterface;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mappers {

    private final ModelMapper modelMapper;

    public UserResponseDto convertToUserResponseDto(User user) {
        return modelMapper.map(user, UserResponseDto.class);
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



    public CartItemResponseDto convertToCartItemResponseDto(CartItem cartItem) {
        modelMapper.typeMap(CartItem.class, CartItemResponseDto.class)
                .addMappings(mapper -> mapper.skip(CartItemResponseDto::setCartResponseDto));
        CartItemResponseDto cartItemResponseDto = modelMapper.map(cartItem, CartItemResponseDto.class);
        cartItemResponseDto.setProductResponseDto(convertToProductResponseDto(cartItem.getProduct()));
        return cartItemResponseDto;
    }

//    public CartItem convertToCartItem(CartItemRequestDto cartItemsDto) {
//        return modelMapper.map(cartItemsDto, CartItem.class);
//    }


    public OrderResponseDto convertToOrderResponseDto(Order order) {
           modelMapper.typeMap(Order.class, OrderResponseDto.class)
            .addMappings(mapper -> mapper.skip(OrderResponseDto::setUserResponseDto));
        return modelMapper.map(order, OrderResponseDto.class);

    }

//    public Order convertToOrder(OrderRequestDto ordersRequestDto) {
//        return modelMapper.map(ordersRequestDto, Order.class);
//    }


    public OrderItemResponseDto convertToOrderItemResponseDto(OrderItem orderItem) {
        modelMapper.typeMap(OrderItem.class, OrderItemResponseDto.class)
                .addMappings(mapper -> mapper.skip(OrderItemResponseDto::setOrderResponseDto));
        OrderItemResponseDto orderItemResponseDto = modelMapper.map(orderItem, OrderItemResponseDto.class);
        orderItemResponseDto.setProductResponseDto(convertToProductResponseDto(orderItem.getProduct()));
        return orderItemResponseDto;
    }

//    public OrderItem convertToOrderItem(OrderItemRequestDto orderItemRequestDto) {
//        return modelMapper.map(orderItemRequestDto, OrderItem.class);
//    }


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


    public ProductCountDto convertToProductCountDto(ProductCountInterface productCountInterface) {
        return modelMapper.map(productCountInterface, ProductCountDto.class);
    }

    public ProductPendingDto convertToProductPendingDto(ProductPendingInterface productPendingInterface) {
        return modelMapper.map(productPendingInterface, ProductPendingDto.class);
    }

    public ProductProfitDto convertToProductProfitDto(ProductProfitInterface productProfitInterface) {
        return modelMapper.map(productProfitInterface, ProductProfitDto.class);
    }


    //   modelMapper.typeMap(CartItem.class, CartItemResponseDto.class)
//            .addMappings(mapper -> mapper.skip(CartItemResponseDto::setCartResponseDto));

}