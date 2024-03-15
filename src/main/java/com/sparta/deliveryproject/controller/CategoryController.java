package com.sparta.deliveryproject.controller;

import com.sparta.deliveryproject.dto.CategoryRequestDto;
import com.sparta.deliveryproject.dto.CategoryResponseDto;
import com.sparta.deliveryproject.dto.CommonResponseDto;
import com.sparta.deliveryproject.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categoryList")
    public ResponseEntity<List<CategoryResponseDto>> getCategoryList(){
        List<CategoryResponseDto> categoryResponseDtoList = categoryService.getCategoryList();
        return ResponseEntity.status(200).body(categoryResponseDtoList);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping()
    public ResponseEntity<CommonResponseDto> createCategory(@RequestBody CategoryRequestDto categoryRequestDto){
        categoryService.createCategory(categoryRequestDto);
        return ResponseEntity.status(200).body(new CommonResponseDto(200, "카테고리 등록 성공"));
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{category_id}")
    public ResponseEntity<CommonResponseDto> editCategory(@RequestBody CategoryRequestDto categoryRequestDto, @PathVariable Long category_id){
        categoryService.editCategory(categoryRequestDto, category_id);
        return ResponseEntity.status(200).body(new CommonResponseDto(200, "카테고리 수정 성공"));
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{category_id}")
    public ResponseEntity<CommonResponseDto> deleteCategory(@PathVariable Long category_id){
        categoryService.deleteCategory(category_id);
        return ResponseEntity.status(200).body(new CommonResponseDto(200, "카테고리 삭제 성공"));
    }

}
