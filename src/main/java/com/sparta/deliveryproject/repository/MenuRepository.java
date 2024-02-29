package com.sparta.deliveryproject.repository;

import com.sparta.deliveryproject.entity.Menu;
import com.sparta.deliveryproject.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByStore(Store store);
    List<Menu> findAllByStoreOrderBySalesCountDesc(Store store);

    List<Menu> findAllByStoreOrderByTotalSalesDesc(Store store);

    void deleteAllByStore(Store store);
}
