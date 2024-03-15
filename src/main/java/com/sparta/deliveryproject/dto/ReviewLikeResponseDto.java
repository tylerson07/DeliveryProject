package com.sparta.deliveryproject.dto;

public class ReviewLikeResponseDto {

    private Long userId;
    private Long reviewId;

    public ReviewLikeResponseDto(Long userId, Long reviewId) {
        this.userId = userId;
        this.reviewId = reviewId;

    }


}
