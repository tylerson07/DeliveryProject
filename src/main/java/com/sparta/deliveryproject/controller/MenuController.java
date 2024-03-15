package com.sparta.deliveryproject.controller;

import com.sparta.deliveryproject.dto.*;
import com.sparta.deliveryproject.exception.DuplicatedMenuException;
import com.sparta.deliveryproject.security.UserDetailsImpl;
import com.sparta.deliveryproject.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menu")
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/store_id/{storeId}")
    public ResponseEntity<List<MenuResponseDto>> getMenuListByStore(@PathVariable Long storeId) {
        List<MenuResponseDto> menuList = menuService.getMenuListByStore(storeId);
        return ResponseEntity.status(200).body(menuList);
    }

    @Secured("ROLE_ENTRE")
    @GetMapping("/{storeId}/sales")
    public ResponseEntity<List<MenuResponseDto>> getTopThreeSalesMenuListByStore(@PathVariable Long storeId) {
        List<MenuResponseDto> menuList = menuService.getTopThreeSalesMenuListByStore(storeId);
        return ResponseEntity.status(200).body(menuList);
    }

    @Secured("ROLE_ENTRE")
    @GetMapping("/{storeId}/counts")
    public ResponseEntity<List<MenuResponseDto>> getTopThreeCountsMenuListByStore(@PathVariable Long storeId) {
        List<MenuResponseDto> menuList = menuService.getTopThreeCountsMenuListByStore(storeId);
        return ResponseEntity.status(200).body(menuList);
    }

    @Secured("ROLE_ENTRE")
    @PostMapping("/{storeId}")
    public ResponseEntity<CommonResponseDto> createMenu(@PathVariable Long storeId, @RequestBody MenuRequestDto menuRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws DuplicatedMenuException {
        menuService.createMenu(storeId, menuRequestDto, userDetails.getUser());
        return ResponseEntity.status(200).body(new CommonResponseDto(200, "메뉴 등록 성공"));
    }

    @Secured("ROLE_ENTRE")
    @PutMapping("/{menuId}")
    public ResponseEntity<CommonResponseDto> editMenu(@PathVariable Long menuId, @RequestBody MenuRequestDto menuRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws DuplicatedMenuException {
        menuService.editMenu(menuId, menuRequestDto, userDetails.getUser());
        return ResponseEntity.status(200).body(new CommonResponseDto(200, "메뉴 수정 성공"));
    }

    @Secured("ROLE_ENTRE")
    @DeleteMapping("/{menuId}")
    public ResponseEntity<CommonResponseDto> deleteStore(@PathVariable Long menuId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        menuService.deleteMenu(menuId, userDetails.getUser());
        return ResponseEntity.status(200).body(new CommonResponseDto(200, "메뉴 삭제 성공"));
    }
}
