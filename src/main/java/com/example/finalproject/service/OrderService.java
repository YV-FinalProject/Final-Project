package com.example.finalproject.service;

import com.example.finalproject.config.*;
import com.example.finalproject.dto.requestdto.*;
import com.example.finalproject.dto.responsedto.*;
import com.example.finalproject.entity.*;
import com.example.finalproject.entity.enums.*;
import com.example.finalproject.exception.*;
import com.example.finalproject.mapper.*;

import com.example.finalproject.repository.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.sql.*;
import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private final Mappers mappers;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public OrderResponseDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            OrderResponseDto orderResponseDto = mappers.convertToOrderResponseDto(order);
            Set<OrderItemResponseDto> orderItemResponseDto = MapperUtil.convertSet(order.getOrderItems(), mappers::convertToOrderItemResponseDto);
            orderResponseDto.setOrderItemsSet(orderItemResponseDto);
            return orderResponseDto;
        } else {
            throw new DataNotFoundInDataBaseException("Order not found in database.");
        }
    }

    public Set<OrderResponseDto> getOrderHistory(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            Set<Order> ordersSet = user.getOrders();
            Set<OrderResponseDto> orderResponseDtoSet = new HashSet<>();
            if (ordersSet != null) {
                for (Order order : ordersSet) {
                    Set<OrderItemResponseDto> orderItemResponseDto = MapperUtil.convertSet(order.getOrderItems(), mappers::convertToOrderItemResponseDto);
                    OrderResponseDto orderResponseDto = mappers.convertToOrderResponseDto(order);
                    orderResponseDto.setOrderItemsSet(orderItemResponseDto);
                    orderResponseDtoSet.add(orderResponseDto);
                }
                return orderResponseDtoSet;
            }
            throw new DataNotFoundInDataBaseException("No orders were placed yet.");
        } else {
            throw new DataNotFoundInDataBaseException("User not found in database.");
        }
    }

    @Transactional
    public void insertOrder(OrderRequestDto orderRequestDto, String email) {
        Order orderToInsert = new Order();

        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {

            orderToInsert.setUser(user);
            orderToInsert.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            orderToInsert.setContactPhone(user.getPhone());
            orderToInsert.setDeliveryAddress(orderRequestDto.getDeliveryAddress());
            orderToInsert.setDeliveryMethod(DeliveryMethod.valueOf(orderRequestDto.getDeliveryMethod()));
            orderToInsert.setStatus(Status.CREATED);
            orderToInsert = orderRepository.save(orderToInsert);

        } else {
            throw new DataNotFoundInDataBaseException("User not found in database.");
        }

        Set<OrderItemRequestDto> orderItemsRequestDtoSet = orderRequestDto.getOrderItemsSet();
        Set<OrderItem> orderItemToInsertSet = new HashSet<>();

        for (OrderItemRequestDto orderItem : orderItemsRequestDtoSet) {
            OrderItem orderItemToInsert = new OrderItem();
            Product product = productRepository.findById(orderItem.getProductId()).orElse(null);
            if (product != null) {
                orderItemToInsert.setProduct(product);
                if (product.getDiscountPrice() == null) {
                    orderItemToInsert.setPriceAtPurchase(product.getPrice());
                } else {
                    orderItemToInsert.setPriceAtPurchase(product.getDiscountPrice());
                }
                orderItemToInsert.setQuantity(orderItem.getQuantity());
                orderItemToInsert.setOrder(orderToInsert);
                orderItemRepository.save(orderItemToInsert);

                orderItemToInsertSet.add(orderItemToInsert);
            } else {
                throw new DataNotFoundInDataBaseException("Product not found in database.");
            }
        }
        orderToInsert.setOrderItems(orderItemToInsertSet);
        orderRepository.save(orderToInsert);

        Cart cart = cartRepository.findById(user.getCart().getCartId()).orElse(null);
        if(cart != null){
            Set <CartItem> cartItemSet = cart.getCartItems();
            for(CartItem item : cartItemSet){
                cartItemRepository.deleteById(item.getCartItemId());
            }
        } else {
            throw new DataNotFoundInDataBaseException("Cart not found in database.");
        }
    }

    public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            if(order.getStatus() == Status.CREATED || order.getStatus() == Status.PENDING_PAYMENT) {
                order.setStatus(Status.CANCELED);
                orderRepository.save(order);
            } else {
                throw new OrderStatusException("Order already paid and can not be canceled.");
            }
        } else {
            throw new DataNotFoundInDataBaseException("Order not found in database.");
        }
    }


}
