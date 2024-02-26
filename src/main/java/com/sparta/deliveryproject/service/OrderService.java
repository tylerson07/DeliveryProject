package com.sparta.deliveryproject.service;

import com.sparta.deliveryproject.dto.OrderRequestDto;
import com.sparta.deliveryproject.dto.OrderResponseDto;
import com.sparta.deliveryproject.entity.Menu;
import com.sparta.deliveryproject.entity.Orders;
import com.sparta.deliveryproject.repository.MenuRepository;
import com.sparta.deliveryproject.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public void createOrders(Long menuId, OrderRequestDto requestDto) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> new NullPointerException("해당 id의 메뉴가 없습니다.")
        );
        Orders orders = new Orders(menu, requestDto);
        orderRepository.save(orders);
    }

    @Transactional
    public OrderResponseDto getOrders() {
        List<Orders> ordersList = orderRepository.findAll();
        return new OrderResponseDto(ordersList);
    }

    @Transactional
    public void updateOrders(Long menuId, OrderRequestDto requestDto) {
        Orders orders = orderRepository.findByMenuId(menuId).orElseThrow(
                () -> new NullPointerException("해당 id의 메뉴가 장바구니에 없습니다.")
        );
        orders.update(requestDto);
    }

    @Transactional
    public void deleteOrders(Long menuId) {
        orderRepository.deleteByMenuId(menuId).orElseThrow(
                () -> new NullPointerException("해당 id의 메뉴가 장바구니에 없습니다.")
        );
    }

    @Transactional
    public void clearOrders() {
        orderRepository.deleteAll();
    }
}