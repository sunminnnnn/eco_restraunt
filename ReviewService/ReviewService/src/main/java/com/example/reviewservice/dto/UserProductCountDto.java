package com.example.reviewservice.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserProductCountDto implements Serializable {
    private String productId;
    private String userId;
}
