package com.example.restaurant_service.controller;

import com.example.restaurant_service.dto.Calculator;
import com.example.restaurant_service.dto.RestaurantDto;
import io.micrometer.core.annotation.Timed;
import jakarta.servlet.http.HttpServletRequest;
import com.example.restaurant_service.jpa.RestaurantEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.restaurant_service.service.RestaurantService;
import com.example.restaurant_service.vo.ResponseRestaurant;

import java.util.ArrayList;
import java.util.List;

@RestController

@RequestMapping("/restaurant-service")
public class RestaurantController {
    private Environment env;
    RestaurantService restaurantService;
    @Autowired
    public RestaurantController(Environment env, RestaurantService restaurantService) {
        this.env = env;
        this.restaurantService = restaurantService;
    }
    @GetMapping("/port_check")
    public String status(HttpServletRequest request) {
        return String.format("Working in Restaurant Service on Port %s", request.getServerPort());
    }

    @GetMapping(value="/update_food")
    public ResponseEntity<List<ResponseRestaurant>> getRestaurants() {
        Iterable<RestaurantEntity> orderList = restaurantService.getAllRestaurants();

        List<ResponseRestaurant> result = new ArrayList<>();
        orderList.forEach(v->{
            result.add(new ModelMapper().map(v, ResponseRestaurant.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @GetMapping(value = "/search/{item}")
    @Timed(value="restaurant.search, longTask = true")
    public ResponseEntity<List<ResponseRestaurant>> search(@PathVariable("item") String keyword) {
        Iterable<RestaurantEntity> orderList = restaurantService.searchRestaurants(keyword);

        List<ResponseRestaurant> result = new ArrayList<>();
        orderList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseRestaurant.class));
        });
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    @GetMapping(path = "/plus/{p1},{p2}")
    public Calculator Plus(@PathVariable String p1, @PathVariable String p2) {
        int c1 = Integer.parseInt(p1);
        int c2 = Integer.parseInt(p2);
        int c3 = c1 + c2;
        return new Calculator(String.format("더한 결과는,%s", c3,"입니다"));
    }

    @GetMapping(path = "/minus/{p1},{p2}")
    public Calculator Minus(@PathVariable String p1, @PathVariable String p2) {
        int c1 = Integer.parseInt(p1);
        int c2 = Integer.parseInt(p2);
        int c3 = c1 - c2;
        return new Calculator(String.format("뺀 결과는,%s", c3,"입니다"));

    }

    @GetMapping(path = "/multi/{p1},{p2}")
    public Calculator Multi(@PathVariable String p1, @PathVariable String p2) {
        int c1 = Integer.parseInt(p1);
        int c2 = Integer.parseInt(p2);
        int c3 = c1 * c2;
        return new Calculator(String.format("곱한 결과는,%s", c3,"입니다"));
    }
    @GetMapping(path="/divide/{p1},{p2}")
    public Calculator Divide(@PathVariable String p1, @PathVariable String p2){
        int c1 = Integer.parseInt(p1);
        int c2 = Integer.parseInt(p2);
        float c3 = ((float) c1 / (float) c2);
        if (c2 == 0) {
            return new Calculator(String.format("0을 나눌 수는 없습니다"));
        } else {
            return new Calculator(String.format("나눈 결과는,%s", c3,"입니다"));
        }

    }


}
