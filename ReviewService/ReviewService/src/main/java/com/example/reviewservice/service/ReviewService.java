package com.example.reviewservice.service;

import java.util.List;

import com.example.reviewservice.dto.ReviewDto;
import com.example.reviewservice.vo.ResponseReview;

public interface ReviewService {
	ReviewDto createReview(ReviewDto reviewDetails);

	List<ResponseReview> getAllReviewsByUserId(String userId);

	String deleteReview(String userId, String reviewId);

	List<ResponseReview> getAllReviews();
}
