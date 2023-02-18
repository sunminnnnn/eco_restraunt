package com.example.restaurant_service.service;

import com.example.restaurant_service.dto.RestaurantDto;
import com.example.restaurant_service.jpa.RestaurantEntity;

public interface RestaurantService {


    Iterable<RestaurantEntity> getAllRestaurants();
    Iterable<RestaurantEntity> searchRestaurants(String keyword);

}
