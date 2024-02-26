package com.sparta.deliveryproject.dto;

import lombok.Getter;

@Getter
public class ReviewRequestDto {
    @NotBlank(message = "내용을 필수로 입력해야 합니다.")
    private String content;
}
