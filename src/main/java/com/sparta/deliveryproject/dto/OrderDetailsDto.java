package com.sparta.deliveryproject.dto;

import com.sparta.deliveryproject.entity.Orders;
import lombok.Getter;

@Getter
public class OrderDetailsDto {
    private Long orderId;
    private String menuName;
    private Long quantity;
    private Long totalPrice;

    public OrderDetailsDto(Orders orders){
        this.orderId = orders.getId();
        this.menuName = orders.getMenu().getName();
        this.quantity = orders.getQuantity();
        this.totalPrice = orders.getTotalPrice();
    }
}
