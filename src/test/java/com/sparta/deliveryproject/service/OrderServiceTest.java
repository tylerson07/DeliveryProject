package com.sparta.deliveryproject.service;

import com.sparta.deliveryproject.dto.OrderRequestDto;
import com.sparta.deliveryproject.dto.OrderResponseDto;
import com.sparta.deliveryproject.entity.Menu;
import com.sparta.deliveryproject.entity.Orders;
import com.sparta.deliveryproject.repository.MenuRepository;
import com.sparta.deliveryproject.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private MenuRepository menuRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Menu menu;
    private Orders orders;
    private Long menuId = 1L;

    @BeforeEach
    void setUp(){
        menu = new Menu(); // 메뉴 초기화 필요
        menu.setId(menuId);
        menu.setPrice(10000); // 예시 가격
    }

    @Test
    @DisplayName("장바구니 등록 - 성공")
    public void testCreateOrders_Success() {
        // given
        OrderRequestDto requestDto = new OrderRequestDto();
        requestDto.setQuantity(1L);

        given(menuRepository.findById(menu.getId())).willReturn(Optional.of(menu));
        given(orderRepository.findByMenuId(menu.getId())).willReturn(Optional.empty());

        // when
        orderService.createOrders(menu.getId(), requestDto);

        // then
        verify(orderRepository).save(any(Orders.class));
    }

    @Test
    @DisplayName("2. 장바구니 등록 - 성공(수량 증가)")
    void testCreateOrders_Success_UpdateQuantity() {
        // given
        OrderRequestDto requestDto = new OrderRequestDto();
        requestDto.setQuantity(1L);

        orders = new Orders(menu, requestDto); // Orders 초기화 필요

        given(menuRepository.findById(menu.getId())).willReturn(Optional.of(menu));
        given(orderRepository.findByMenuId(menu.getId())).willReturn(Optional.of(orders));

        // when
        orderService.createOrders(menu.getId(), requestDto);

        // then
        assertEquals(2L, orders.getQuantity());
    }

    @Test
    @DisplayName("3. 장바구니 등록 - 실패(수량 검증 에러)")
    void testCreateOrders_Fail_QuantityCheck() {
        // given
        OrderRequestDto requestDto = new OrderRequestDto();
        requestDto.setQuantity(0L);

        given(menuRepository.findById(menu.getId())).willReturn(Optional.of(menu));

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.createOrders(menu.getId(), requestDto);
        });

        // then
        assertTrue(exception.getMessage().contains("수량은 1개 이상이어야 합니다."));
    }

    @Test
    @DisplayName("4. 장바구니 조회 - 성공")
    void testGetOrders_Success() {
        // given
        OrderRequestDto requestDto1 = new OrderRequestDto();
        OrderRequestDto requestDto2 = new OrderRequestDto();
        requestDto1.setQuantity(1L);
        requestDto2.setQuantity(2L);
        List<Orders> ordersList = new ArrayList<>();
        ordersList.add(new Orders(menu, requestDto1));
        ordersList.add(new Orders(menu, requestDto2));

        given(orderRepository.findAll()).willReturn(ordersList);

        // when
        OrderResponseDto result = orderService.getOrders();

        // then
        assertFalse(result.getOrdersList().isEmpty());
        assertEquals(2, result.getOrdersList().size());
    }

    @Test
    @DisplayName("5. 장바구니 수정 - 성공")
    void testUpdateOrders_Success() {
        // given
        OrderRequestDto requestDto = new OrderRequestDto();
        requestDto.setQuantity(2L);

        orders = new Orders(menu, requestDto);

        given(orderRepository.findByMenuId(menu.getId())).willReturn(Optional.of(orders));

        // when
        orderService.updateOrders(menu.getId(), requestDto);

        // then
        assertEquals(2L, orders.getQuantity());
    }

    @Test
    @DisplayName("6. 장바구니 수정 - 실패(수량 검증 에러)")
    void testUpdateOrders_Fail_QuantityCheck() {
        // given
        OrderRequestDto requestDto = new OrderRequestDto();
        requestDto.setQuantity(0L);

        given(orderRepository.findByMenuId(menu.getId())).willReturn(Optional.of(new Orders(menu, requestDto)));

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.updateOrders(menu.getId(), requestDto);
        });

        // then
        assertTrue(exception.getMessage().contains("수량은 1개 이상이어야 합니다."));
    }

    @Test
    @DisplayName("7. 장바구니 삭제 - 성공")
    void testDeleteOrder_Success() {
        // given
        given(orderRepository.deleteByMenuId(menuId)).willReturn(Optional.of(new Orders()));

        // when
        orderService.deleteOrders(menuId);

        // then
        verify(orderRepository).deleteByMenuId(menuId);
    }

    @Test
    @DisplayName("8. 장바구니 비우기 - 성공")
    void testClearOrders_Success() {
        // given
        doNothing().when(orderRepository).deleteAll();

        // when
        orderService.clearOrders();

        // then
        verify(orderRepository).deleteAll();
    }

}