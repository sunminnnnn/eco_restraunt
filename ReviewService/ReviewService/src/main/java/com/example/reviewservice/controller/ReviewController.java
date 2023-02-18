package com.example.reviewservice.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reviewservice.dto.ReviewDto;
import com.example.reviewservice.messagequeue.KafkaProducer;
import com.example.reviewservice.service.ReviewService;
import com.example.reviewservice.vo.RequestReview;
import com.example.reviewservice.vo.ResponseReview;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/review-service")
@RequiredArgsConstructor

public class ReviewController {
	private final Environment env;
	private final ReviewService reviewService;
	private final KafkaProducer kafkaProducer;

	@GetMapping("/health_check")
	public String status() {
		return String.format("It's working in review Service on PORT %s",
			env.getProperty("local.server.port"));
	}

	//개별리뷰생성
	@PostMapping(value = "/{userId}")
	public ResponseEntity<ResponseReview> createReview(@PathVariable("userId") String userId,
		@RequestBody RequestReview reviewDetails) {
		System.out.println("create review In Review");
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy((MatchingStrategies.STRICT));

		ReviewDto reviewDto = modelMapper.map(reviewDetails, ReviewDto.class);
		reviewDto.setUserId(userId);
		ReviewDto creteDto = reviewService.createReview(reviewDto);
		ResponseReview returnValue = modelMapper.map(creteDto, ResponseReview.class);

		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
	}

	//개별 리뷰보기
	@GetMapping(value = "/{userId}")
	public ResponseEntity<List<ResponseReview>> getReview(@PathVariable("userId") String userId) {
		List<ResponseReview> reviewList = reviewService.getAllReviewsByUserId(userId);
		return ResponseEntity.status(HttpStatus.OK).body(reviewList);
	}

	//    개별리뷰 삭제
	@DeleteMapping(value = "/{userId}/{reviewId}")
	public ResponseEntity<String> deleteReview(@PathVariable String userId, @PathVariable String reviewId) {
		String message = reviewService.deleteReview(userId, reviewId);
		return ResponseEntity.ok(message);
	}

	// 전체리뷰보기
	@GetMapping(value = "/total")
	public ResponseEntity<List<ResponseReview>> getAllReviews() {
        List<ResponseReview> allReviews = reviewService.getAllReviews();
        return ResponseEntity.ok(allReviews);
    }
}
