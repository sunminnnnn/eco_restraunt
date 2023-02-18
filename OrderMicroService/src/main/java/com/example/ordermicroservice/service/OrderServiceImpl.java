package com.example.ordermicroservice.service;

import com.example.ordermicroservice.dto.OrderDto;
import com.example.ordermicroservice.jpa.OrderEntity;
import com.example.ordermicroservice.jpa.OrderRepository;
import com.example.ordermicroservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    OrderRepository repository;
    Environment env;
    CircuitBreakerFactory circuitBreakerFactory;
    @Autowired
    public OrderServiceImpl(OrderRepository repository, Environment env,
                            CircuitBreakerFactory circuitBreakerFactory) {
        this.env = env;
        this.repository = repository;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDetails) {
        orderDetails.setOrderId((UUID.randomUUID().toString()));
        orderDetails.setTotalPrice(orderDetails.getQty() * orderDetails.getUnitPrice());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderEntity orderEntity = modelMapper.map(orderDetails, OrderEntity.class);

        repository.save(orderEntity);

        OrderDto returnValue = modelMapper.map(orderEntity, OrderDto.class);
        return returnValue;
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        OrderEntity orderEntity = repository.findByOrderId(orderId);
        OrderDto orderDto = new ModelMapper().map(orderEntity, OrderDto.class);
//        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
//        List<ResponseOrder> orderList = circuitBreaker.run(() -> orderServiceClient.getOrders(orderId),
//                throwable -> new ArrayList<>());
        return orderDto;
    }

    @Override
    public Iterable<OrderEntity> getOrdersByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public Iterable<OrderEntity> searchOrders(String keyword) {
        return repository.findByProductIdContaining(keyword);
    }

    @Override
    public OrderDto deleteOrder(OrderDto orderDto) {
        OrderEntity orderEntity = repository.findByOrderId(orderDto.getOrderId());
        if(orderDto != null) {
            repository.delete(orderEntity);
        }
        return orderDto;
    }
}
