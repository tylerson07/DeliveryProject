package com.sparta.deliveryproject.controller;

import com.sparta.deliveryproject.dto.CommonResponseDto;
import com.sparta.deliveryproject.dto.ReviewListResponseDto;
import com.sparta.deliveryproject.dto.ReviewRequestDto;
import com.sparta.deliveryproject.dto.ReviewResponseDto;
import com.sparta.deliveryproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/review/{store_id}")
public class ReviewController {
    private final ReviewService reviewService;

    //리뷰조회
    @GetMapping()
    public ResponseEntity<ReviewListResponseDto> getReviewDetail(@PathVariable Long id) {
        List<ReviewResponseDto> reviewList = reviewService.getReviewDetail(id);
        return ResponseEntity.status(200).body(reviewList);
    }

    //리뷰등록
    @PostMapping()
    public  ResponseEntity<CommonResponseDto> review(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable(name = "storeId") Long storeId, @RequestBody ReviewRequestDto reviewRequestDto) {
        reviewService.review(reviewRequestDto, storeId, userDetails);
        return ResponseEntity.status(200).body(new CommonResponseDto(200, "리뷰 등록 성공"));
    }

    //리뷰수정
    @PutMapping("/{reviewId}")
    public ResponseEntity<CommonResponseDto> updateReview(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable(name = "reviewId") Long reviewId, @RequestBody ReviewRequestDto reviewRequestDto, @PathVariable(name = "storeId") Long storeId) {
        reviewService.updateReview(reviewRequestDto, storeId, reviewId,userDetails);
        return ResponseEntity.status(200).body(new CommonResponseDto(200, "리뷰 수정 성공"));
    }

    //리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<CommonResponseDto> deleteReview(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable(name = "reviewId") Long reviewId, @PathVariable(name = "storeId") Long storeId) {
        reviewService.deleteReview(storeId, reviewId, userDetails);
        return ResponseEntity.status(200).body(new CommonResponseDto(200, "리뷰 삭제 성공"));
    }

}
