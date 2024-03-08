package com.sparta.deliveryproject.service;

import com.sparta.deliveryproject.dto.CategoryRequestDto;
import com.sparta.deliveryproject.dto.CategoryResponseDto;
import com.sparta.deliveryproject.entity.Category;
import com.sparta.deliveryproject.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getCategoryList() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream()
                .map(CategoryResponseDto::new)
                .toList();
    }

    @Override
    public void createCategory(CategoryRequestDto categoryRequestDto) {
        if( categoryRepository.findByName(categoryRequestDto.getName()).isPresent()){
            throw new IllegalArgumentException("동일한 카테고리가 존재합니다.");
        }

        Category category = new Category(categoryRequestDto);
        categoryRepository.save(category);
    }

    @Override
    public void editCategory(CategoryRequestDto categoryRequestDto, Long category_id) {
       Category category = categoryRepository.findById(category_id).orElseThrow(
               ()->  new NullPointerException("해당 이름의 카테고리가 존재하지 않습니다.")
       );

       category.update(categoryRequestDto);
    }

    @Override
    public void deleteCategory(Long category_id) {
        Category category = categoryRepository.findById(category_id).orElseThrow(
                ()->  new NullPointerException("해당 이름의 카테고리가 존재하지 않습니다.")
        );
        categoryRepository.delete(category);
    }
}
