package com.sparta.deliveryproject.controller;

import com.sparta.deliveryproject.dto.CommonResponseDto;
import com.sparta.deliveryproject.dto.ReviewListResponseDto;
import com.sparta.deliveryproject.dto.ReviewRequestDto;
import com.sparta.deliveryproject.dto.ReviewResponseDto;
import com.sparta.deliveryproject.security.UserDetailsImpl;
import com.sparta.deliveryproject.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/review/{menuId}")
public class ReviewController {
    private final ReviewService reviewService;

    //리뷰조회
    @GetMapping()
    public ResponseEntity<ReviewListResponseDto> getReviewDetail(@PathVariable Long menuId) {
        ReviewListResponseDto reviewList = reviewService.getReviewDetail(menuId);
        return ResponseEntity.status(200).body(reviewList);
    }

    //리뷰등록
    @PostMapping()
    public ResponseEntity<CommonResponseDto> review(@PathVariable Long menuId, @RequestBody ReviewRequestDto reviewRequestDto ,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        reviewService.review(reviewRequestDto, menuId, userDetails);
        return ResponseEntity.status(200).body(new CommonResponseDto(200, "리뷰 등록 성공"));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<CommonResponseDto> updateReview(@PathVariable Long menuId, @PathVariable Long reviewId, @RequestBody ReviewRequestDto reviewRequestDto ,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        reviewService.updateReview(reviewRequestDto,menuId, reviewId, userDetails);
        return ResponseEntity.status(200).body(new CommonResponseDto(200, "리뷰 수정 성공"));
    }

    //리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<CommonResponseDto> deleteReview(@PathVariable Long menuId, @PathVariable Long reviewId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        reviewService.deleteReview(menuId,reviewId, userDetails);
        return ResponseEntity.status(200).body(new CommonResponseDto(200, "리뷰 삭제 성공"));
    }

}