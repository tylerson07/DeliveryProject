package com.sparta.deliveryproject.repository;

import com.sparta.deliveryproject.config.JPAConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JPAConfiguration.class)
@Rollback(value = false)
class MenuRepositoryTest {

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    StoreRepository storeRepository;

    @Test
    void findAllByStoreTest() {
        var store = storeRepository.findById(13L).orElse(null);
        assert store != null;

        Sort.Direction direction = Sort.Direction.ASC;
        Sort sort = Sort.by(direction, "salesCount");
        Pageable pageable = PageRequest.of(1, 2, sort);

        var stores = menuRepository.findAllByStore(store, pageable);
        assert !stores.isEmpty();
    }

    @Test
    void findAllByStoreTest2() {
        var store = storeRepository.findById(13L).orElse(null);
        assert store != null;

        var stores = menuRepository.findAllByStore(store);
        assert !stores.isEmpty();
    }

    @Test
    void getTopThreeSalesMenuListByStoreTest() {
        var store = storeRepository.findById(13L).orElse(null);
        assert store != null;
        var menus = menuRepository.getTopThreeSalesMenuListByStore(store.getId());
        assert !menus.isEmpty();
    }

    @Test
    void getTopThreeCountsMenuListByStoreTest() {
        var store = storeRepository.findById(13L).orElse(null);
        assert store != null;
        var menus = menuRepository.getTopThreeCountsMenuListByStore(store.getId());
        assert !menus.isEmpty();
    }
}