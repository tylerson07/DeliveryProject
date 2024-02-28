package com.sparta.deliveryproject.dto;

import com.sparta.deliveryproject.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryResponseDto {
    private String name;
    private String introduce;

    public CategoryResponseDto(Category category) {
        this.name = category.getName();
        this.introduce = category.getIntroduce();
    }
}
