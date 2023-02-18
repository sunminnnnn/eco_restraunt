package com.example.restaurant_service.jpa;

import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<RestaurantEntity, Long> {
    RestaurantEntity findByProductId(String productId);

    Iterable<RestaurantEntity> findByProductIdContaining(String keyword);
}
