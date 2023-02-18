package com.example.reviewservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.reviewservice.dto.ReviewDto;
import com.example.reviewservice.jpa.ReviewEntity;
import com.example.reviewservice.jpa.ReviewRepository;
import com.example.reviewservice.vo.ResponseReview;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class ReviewServiceImpl implements ReviewService {
    ReviewRepository repository;
    Environment env;

    @Autowired
    public ReviewServiceImpl(ReviewRepository repository, Environment env) {
        this.env = env;
        this.repository = repository;
    }

    @Override
    public ReviewDto createReview(ReviewDto reviewDetails) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        ReviewEntity reviewEntity = modelMapper.map(reviewDetails, ReviewEntity.class);
        reviewEntity.setReviewId(UUID.randomUUID().toString());
        repository.save(reviewEntity);

        ReviewDto returnValue = modelMapper.map(reviewEntity, ReviewDto.class);
        return returnValue;
    }


    @Override
    public List<ResponseReview> getAllReviewsByUserId(String userId) {
        Iterable<ReviewEntity> byUserId = repository.findByUserId(userId);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy((MatchingStrategies.STRICT));
        List<ResponseReview> list = new ArrayList<>();
        for (ReviewEntity reviewEntity : byUserId) {
            list.add(modelMapper.map(reviewEntity, ResponseReview.class));
        }
        return list;
    }

    @Override
    public String deleteReview(String userId, String reviewId) {
        ReviewEntity review = repository.findByReviewId(reviewId);
        if (review == null || !review.getUserId().equals(userId)) {
            return "리뷰 삭제를 실패하였습니다.";
        }

        repository.deleteById(review.getId());
        return "리뷰 삭제를 완료하였습니다.";
    }

    @Override
    public List<ResponseReview> getAllReviews() {
        Iterable<ReviewEntity> all = repository.findAll();
        List<ResponseReview> list = new ArrayList<>();

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy((MatchingStrategies.STRICT));
        for (ReviewEntity review : all) {
            list.add(modelMapper.map(review, ResponseReview.class));
        }
        return list;
    }

}
