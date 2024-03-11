package com.sparta.deliveryproject.repositoryQuery;

import com.sparta.deliveryproject.entity.User;
import com.sparta.deliveryproject.responseDto.StoreResponseDto;

import java.util.List;

public interface StoreRepositoryQuery {
    List<StoreResponseDto> getTopCountStoreList(User user);

    List<StoreResponseDto> getTopSalesStoreList(User user);
}
