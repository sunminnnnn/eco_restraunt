package com.example.reviewservice.jpa;

import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<ReviewEntity, Long> {
    Iterable<ReviewEntity> findByUserId(String userId);

    // select * from review where reviewId = 'reviewId';
    // id, title, content, userId
    ReviewEntity findByReviewId(String reviewId);
}
