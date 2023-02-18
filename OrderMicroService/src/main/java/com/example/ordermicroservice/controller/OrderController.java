package com.example.ordermicroservice.controller;

import com.example.ordermicroservice.dto.OrderDto;
import com.example.ordermicroservice.jpa.OrderEntity;
import com.example.ordermicroservice.service.OrderService;
import com.example.ordermicroservice.vo.RequestOrder;
import com.example.ordermicroservice.vo.ResponseOrder;
import java.util.ArrayList;
import java.util.List;

import com.example.ordermicroservice.messagequeue.KafkaProducer;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order-service")
public class OrderController {
    Environment env;
    OrderService orderService;
    KafkaProducer kafkaProducer;

    @Autowired
    public OrderController(Environment env, OrderService orderService, KafkaProducer kafkaProducer) {
        this.env = env;
        this.orderService = orderService;
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's working in Order Service on PORT %s",
                env.getProperty("local.server.port"));
    }

    @PostMapping(value = "/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId,
                                                     @RequestBody RequestOrder orderDetails) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy((MatchingStrategies.STRICT));

        OrderDto orderDto = modelMapper.map(orderDetails,OrderDto.class);
        orderDto.setUserId(userId);
        OrderDto creteDto = orderService.createOrder(orderDto);
        ResponseOrder returnValue = modelMapper.map(creteDto, ResponseOrder.class);

        /* Send an order to the Kafka */
        kafkaProducer.send("example-order-topic-6", orderDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @GetMapping(value = "/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable("userId") String userId,
                                                        @RequestBody RequestOrder orderDetails) {
        Iterable<OrderEntity> orderList = orderService.getOrdersByUserId(userId);

        List<ResponseOrder> result = new ArrayList<>();
        orderList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseOrder.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping(value = "/search/{keyword}")
    public ResponseEntity<List<ResponseOrder>> search(@PathVariable("keyword") String keyword) {
        Iterable<OrderEntity> orderList = orderService.searchOrders(keyword);

        List<ResponseOrder> result = new ArrayList<>();
        orderList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseOrder.class));
        });
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping(value="/order/delete/{orderId}")
    public String deleteUser(@PathVariable("orderId") String orderId, OrderDto orderDto){
        orderService.deleteOrder(orderDto);

        return "Delete order method is called";
    }
}
