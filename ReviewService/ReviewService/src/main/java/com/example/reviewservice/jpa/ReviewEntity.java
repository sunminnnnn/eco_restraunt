package com.example.reviewservice.jpa;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "reviews")
public class ReviewEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String reviewId;
    @Column(nullable = false)
    private String reviewTitle;
    @Column(nullable = false, length = 330)
    private String reviewContent;
    @Column(nullable = false)
    private String userId;


    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value="CURRENT_TIMESTAMP")
    private Date createAt;
}
