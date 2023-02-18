package com.example.restaurant_service.service;

import com.example.restaurant_service.dto.RestaurantDto;
import com.example.restaurant_service.jpa.RestaurantEntity;
import com.example.restaurant_service.jpa.RestaurantRepository;
import com.example.restaurant_service.vo.ResponseRestaurant;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class RestaurantServiceImpl implements RestaurantService{
    RestaurantRepository restaurantRepository;

    Environment env;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, Environment env) {
        this.restaurantRepository = restaurantRepository;
        this.env = env;
    }



    public Iterable<RestaurantEntity> getAllRestaurants() {
        return restaurantRepository.findAll();
    }
    public Iterable<RestaurantEntity> searchRestaurants(String keyword) {
        return restaurantRepository.findByProductIdContaining(keyword);
    }


}







