package com.example.ordermicroservice.jpa;

import org.springframework.data.repository.CrudRepository;

import javax.persistence.Entity;


public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    OrderEntity findByOrderId(String orderId);
    Iterable<OrderEntity> findByUserId(String userId);
    Iterable<OrderEntity> findByProductIdContaining(String keyword);
}
