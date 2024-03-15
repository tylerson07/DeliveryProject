package com.sparta.deliveryproject.dto;


import com.sparta.deliveryproject.entity.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.sparta.deliveryproject.entity.Store;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ReviewListResponseDto {
    private Long id;
    private List<ReviewResponseDto> reviewList;

    public  ReviewListResponseDto(Menu menu, List<ReviewResponseDto> reviewList) {
     this.id = menu.getId();
     this.reviewList = reviewList;
      }
}
