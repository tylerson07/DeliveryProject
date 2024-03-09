package com.sparta.deliveryproject.entity;

import com.sparta.deliveryproject.requestDto.CategoryRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "category")
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String introduce;

    public Category(CategoryRequestDto categoryRequestDto) {
        this.name = categoryRequestDto.getName();
        this.introduce = categoryRequestDto.getIntroduce();
    }

    public void update(CategoryRequestDto categoryRequestDto) {
        this.name = categoryRequestDto.getName();
        this.introduce = categoryRequestDto.getIntroduce();
    }
}
