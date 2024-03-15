package com.sparta.deliveryproject.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StoreRequestDto {
    private String name;
    private String address;
    private String introduce;
    private String category;
}
