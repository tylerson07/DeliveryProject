package com.sparta.deliveryproject.controller;

import com.sparta.deliveryproject.dto.CommonResponseDto;
import com.sparta.deliveryproject.dto.OrderRequestDto;
import com.sparta.deliveryproject.dto.OrderResponseDto;
import com.sparta.deliveryproject.security.UserDetailsImpl;
import com.sparta.deliveryproject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping()
    public ResponseEntity<OrderResponseDto> getOrders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        OrderResponseDto responseDto = orderService.getOrders(userDetails.getUser());
        return ResponseEntity.status(200).body(responseDto);
    }

    @PostMapping("/{menuId}")
    public ResponseEntity<CommonResponseDto> createOrders(@PathVariable Long menuId, @RequestBody OrderRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        orderService.createOrders(menuId, requestDto, userDetails.getUser());
        return ResponseEntity.status(200).body(new CommonResponseDto(200, "장바구니 등록 성공"));
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<CommonResponseDto> updateOrders(@PathVariable Long menuId, @RequestBody OrderRequestDto requestDto,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.updateOrders(menuId, requestDto, userDetails.getUser());
        return ResponseEntity.status(200).body(new CommonResponseDto(200, "장바구니 메뉴 수정 성공"));
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<CommonResponseDto> deleteOrders(@PathVariable Long menuId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.deleteOrders(menuId, userDetails.getUser());
        return ResponseEntity.status(200).body(new CommonResponseDto(200, "장바구니 메뉴 삭제 성공"));
    }

    @DeleteMapping()
    public ResponseEntity<CommonResponseDto> clearOrders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.clearOrders(userDetails.getUser());
        return ResponseEntity.status(200).body(new CommonResponseDto(200, "장바구니 비우기 성공"));
    }

    @GetMapping("/purchase")
    public ResponseEntity<CommonResponseDto> purchaseOrders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.purchaseOrders(userDetails.getUser());
        return ResponseEntity.status(200).body(new CommonResponseDto(200, "장바구니 메뉴 구매 성공"));
    }
}
