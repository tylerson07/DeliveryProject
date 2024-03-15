package com.sparta.deliveryproject.service;

import com.sparta.deliveryproject.dto.ReviewLikeResponseDto;
import com.sparta.deliveryproject.entity.Review;
import com.sparta.deliveryproject.entity.ReviewLikes;
import com.sparta.deliveryproject.entity.User;
import com.sparta.deliveryproject.repository.*;
import com.sparta.deliveryproject.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewLikesService {
    private final ReviewRepository reviewRepository;
    private final ReviewLikesRepository reviewLikesRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // 리뷰 좋아요 등록
    public ReviewLikeResponseDto likeReview(Long reviewId, UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getUserID();
        User user = userRepository.findById(userId).orElseThrow();
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new NoSuchElementException("해당 리뷰를 찾을 수 없습니다.")
        );

        if(reviewLikesRepository.findByUserAndReview(user, review).isPresent()) {
            throw new IllegalArgumentException("이미 좋아요한 리뷰입니다.");
        }

        ReviewLikes reviewLikes = new ReviewLikes(user, review);
        reviewLikesRepository.save(reviewLikes);

        return new ReviewLikeResponseDto(reviewId ,user.getUserID());
    }

    // 리뷰 좋아요 삭제
    public void likeDeleteReview(Long reviewId, UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getUserID();
        User user = userRepository.findById(userId).orElseThrow();
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new NoSuchElementException("해당 리뷰를 찾을 수 없습니다.")
        );

        ReviewLikes reviewLikes = reviewLikesRepository.findByUserAndReview(user, review).orElseThrow(
                () -> new NoSuchElementException("해당 리뷰에 좋아요를 하지 않았습니다.")
        );

        reviewLikesRepository.deleteById(reviewLikes.getId());
    }

    //리뷰 좋아요 갯수 출력
    public int getReviewLikesCount(Long reviewId) {
        return reviewLikesRepository.countByReview_Id(reviewId);
    }


}
