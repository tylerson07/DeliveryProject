package com.sparta.deliveryproject.dto;

import com.sparta.deliveryproject.entity.Review;
import lombok.Getter;

@Getter
public class ReviewResponseDto {
    private String content;
    private String username;

    public ReviewResponseDto(Review review) {
        this.content = review.getContent();
        this.username = review.getUser().getUsername();
    }

}
