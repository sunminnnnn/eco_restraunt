package com.example.restaurant_service.dto;

import java.io.Serializable;

public class RestaurantDto implements Serializable {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;


}
