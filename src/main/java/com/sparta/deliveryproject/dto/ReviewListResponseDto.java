package com.sparta.deliveryproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewListResponseDto {
    private Long id;
    private String username;
    private List<ReviewResponseDto> reviewList;

    public  ReviewListResponseDto(Store store, List<ReviewResponseDto> reviewList) {
        this.id = store.getId();
        this.username = store.getUser().getUsername();
        this.reviewList = reviewList;
    }


}
