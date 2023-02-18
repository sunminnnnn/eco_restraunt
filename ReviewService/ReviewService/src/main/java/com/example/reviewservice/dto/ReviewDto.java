package com.example.reviewservice.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class ReviewDto implements Serializable {
    private String userId;
    private String reviewId;
    private String reviewTitle;
    private String reviewContent;
}
