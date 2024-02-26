package com.sparta.deliveryproject.repository;

import java.util.List;

public class ReviewRepository {
    List<Review> findAllByStoreId(Long storeId);
}
