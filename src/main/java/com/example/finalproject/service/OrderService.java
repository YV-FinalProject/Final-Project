package com.example.finalproject.service;

import com.example.finalproject.config.MapperUtil;
import com.example.finalproject.dto.requestdto.OrderItemRequestDto;
import com.example.finalproject.dto.requestdto.OrderRequestDto;
import com.example.finalproject.dto.responsedto.OrderItemResponseDto;
import com.example.finalproject.dto.responsedto.OrderResponseDto;
import com.example.finalproject.entity.*;
import com.example.finalproject.entity.enums.DeliveryMethod;
import com.example.finalproject.entity.enums.Status;
import com.example.finalproject.exception.DataNotFoundInDataBaseException;
import com.example.finalproject.mapper.Mappers;
import com.example.finalproject.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private final Mappers mappers;

    @Transactional
    public OrderResponseDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            OrderResponseDto orderResponseDto = mappers.convertToOrderResponseDto(order);
            Set<OrderItemResponseDto> orderItemResponseDto = MapperUtil.convertSet(order.getOrderItems(), mappers::convertToOrderItemResponseDto);
            orderResponseDto.setOrderItemsSet(orderItemResponseDto);
            return orderResponseDto;
        } else {
            throw new DataNotFoundInDataBaseException("Data not found in database.");
        }
    }

    @Transactional
    public Set<OrderResponseDto> getOrderHistoryByUserId(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
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
            throw new DataNotFoundInDataBaseException("Data not found in database.");
        } else {
            throw new DataNotFoundInDataBaseException("Data not found in database.");
        }
    }

    @Transactional
    public void insertOrder(OrderRequestDto orderRequestDto, Long userId) {
        Order orderToInsert = new Order();

        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {

            orderToInsert.setUser(user);
            orderToInsert.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            orderToInsert.setContactPhone(user.getPhoneNumber());
            orderToInsert.setDeliveryAddress(orderRequestDto.getDeliveryAddress());
            orderToInsert.setDeliveryMethod(DeliveryMethod.valueOf(orderRequestDto.getDeliveryMethod()));
            orderToInsert.setStatus(Status.CREATED);
            orderToInsert = orderRepository.save(orderToInsert);

        } else {
            throw new DataNotFoundInDataBaseException("Data not found in database.");
        }

        Set<OrderItemRequestDto> orderItemsRequestDtoSet = orderRequestDto.getOrderItemsSet();
        Set<OrderItem> orderItemToInsertSet = new HashSet<>();
        OrderItem orderItemToInsert = new OrderItem();

        for (OrderItemRequestDto orderItem : orderItemsRequestDtoSet) {
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
                throw new DataNotFoundInDataBaseException("Data not found in database.");
            }
        }
        orderToInsert.setOrderItems(orderItemToInsertSet);
        orderRepository.save(orderToInsert);
    }
}
