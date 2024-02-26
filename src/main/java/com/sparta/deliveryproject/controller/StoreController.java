package com.sparta.deliveryproject.controller;

import com.sparta.deliveryproject.dto.CommonResponseDto;
import com.sparta.deliveryproject.dto.StoreRequestDto;
import com.sparta.deliveryproject.dto.StoreResponseDto;
import com.sparta.deliveryproject.entity.CategoryEnum;
import com.sparta.deliveryproject.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store")
public class StoreController {

    private final StoreService storeService;

    // 카테고리별 메장 조회
    @GetMapping("/{category}")
    public ResponseEntity<List<StoreResponseDto>> getStoreListByCategory(@PathVariable CategoryEnum category) {
        List<StoreResponseDto> storeList = storeService.getStoreListByCategory(category);
        return ResponseEntity.status(200).body(storeList);
    }

    // 매장 등록
    @PostMapping()
    public ResponseEntity<CommonResponseDto> createStore(@RequestBody StoreRequestDto storeRequestDto) {
        storeService.createStore(storeRequestDto);
        return ResponseEntity.status(200).body(new CommonResponseDto(200, "매장 등록 성공"));
    }

    // 매장 수정
    @PutMapping("/{storeId}")
    public ResponseEntity<CommonResponseDto> editStore(@PathVariable Long storeId, @RequestBody StoreRequestDto storeRequestDto) {
        storeService.editStore(storeId, storeRequestDto);
        return ResponseEntity.status(200).body(new CommonResponseDto(200, "매장 수정 성공"));
    }

    // 매장 삭제
    @DeleteMapping("/{storeId}")
    public ResponseEntity<CommonResponseDto> deleteStore(@PathVariable Long storeId) {
        storeService.deleteStore(storeId);
        return ResponseEntity.status(200).body(new CommonResponseDto(200, "매장 삭제 성공"));
    }
}