package com.sparta.deliveryproject.controller;

import com.sparta.deliveryproject.dto.StoreRequestDto;
import com.sparta.deliveryproject.dto.StoreResponseDto;
import com.sparta.deliveryproject.security.UserDetailsImpl;
import com.sparta.deliveryproject.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StoreController {

    private final StoreService storeService;

    // 카테고리별 메장 조회
    @GetMapping("/public/store/category/{categoryId}")
    public ResponseEntity<List<StoreResponseDto>> getStoreListByCategory(@PathVariable Long categoryId) {
        List<StoreResponseDto> storeList = storeService.getStoreListByCategory(categoryId);
        return ResponseEntity.status(200).body(storeList);
    }

    @Secured("ROLE_ENTRE")
    @GetMapping("entre/sales")
    public ResponseEntity<List<StoreResponseDto>> getTopSalesStoreList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<StoreResponseDto> storeList = storeService.getTopSalesStoreList(userDetails.getUser());
        return ResponseEntity.status(200).body(storeList);
    }

    @Secured("ROLE_ENTRE")
    @GetMapping("entre/count")
    public ResponseEntity<List<StoreResponseDto>> getTopCountStoreList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<StoreResponseDto> storeList = storeService.getTopCountStoreList(userDetails.getUser());
        return ResponseEntity.status(200).body(storeList);
    }

    // 매장 등록
    @Secured("ROLE_ENTRE")
    @PostMapping()
    public void createStore(@RequestBody StoreRequestDto storeRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        storeService.createStore(storeRequestDto, userDetails.getUser());
    }

    // 매장 수정
    @Secured("ROLE_ENTRE")
    @PutMapping("/{storeId}")
    public void editStore(@PathVariable Long storeId, @RequestBody StoreRequestDto storeRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        storeService.editStore(storeId, storeRequestDto, userDetails.getUser());
    }

    // 매장 삭제
    @Secured("ROLE_ENTRE")
    @DeleteMapping("/{storeId}")
    public void deleteStore(@PathVariable Long storeId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        storeService.deleteStore(storeId, userDetails.getUser());
    }
}
