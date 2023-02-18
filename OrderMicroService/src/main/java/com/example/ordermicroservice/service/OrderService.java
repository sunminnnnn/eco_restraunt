package com.example.ordermicroservice.service;

import com.example.ordermicroservice.dto.OrderDto;
import com.example.ordermicroservice.jpa.OrderEntity;
import com.example.ordermicroservice.vo.ResponseOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface OrderService {
    OrderDto createOrder(OrderDto orderDetails);
    OrderDto getOrderByOrderId(String orderId);
    Iterable<OrderEntity> getOrdersByUserId(String userId);
    Iterable<OrderEntity> searchOrders(String keyword);

    OrderDto deleteOrder(OrderDto orderDto);
}
