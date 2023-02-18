package com.example.restaurant_service.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseRestaurant {
    private String productId;
    private String productName;
    private Integer stock;
    private Integer unitPrice;

    private Date createdAt;
}
