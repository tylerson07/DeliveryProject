package com.sparta.deliveryproject.repository;

import com.sparta.deliveryproject.entity.CategoryEnum;
import com.sparta.deliveryproject.entity.Menu;
import com.sparta.deliveryproject.entity.Orders;
import com.sparta.deliveryproject.entity.Store;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    private Menu menu;
    private Store store;

    @BeforeEach
    void setUp() {
        store = new Store();
        store.setCategory(CategoryEnum.KOREAN);
        store.setName("test");
        store.setAddress("test");
        store.setIntroduce("test");
        entityManager.persist(store);

        menu = new Menu();
        menu.setName("test");
        menu.setPrice(10000);
        menu.setStore(store);
        menu.setIntroduce("test");
        entityManager.persist(menu);
    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }

    @Test
    void whenSaveOrder_thenFindById() {
        // given
        Orders order = new Orders();
        order.setMenu(menu);
        order.setQuantity(2L);
        order.setTotalPrice(20000L);
        entityManager.persist(order);

        // when
        Optional<Orders> foundOrder = orderRepository.findByMenuId(menu.getId());

        // then
        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get().getMenu().getId()).isEqualTo(menu.getId());
    }

    @Test
    @Transactional
    void whenDeleteByMenuId_thenNotFound() {
        // given
        Orders order = new Orders();
        order.setMenu(menu);
        order.setQuantity(1L);
        order.setTotalPrice(10000L);
        entityManager.persist(order);

        // when
        orderRepository.deleteByMenuId(menu.getId());
        Optional<Orders> deletedOrder = orderRepository.findByMenuId(menu.getId());

        // then
        assertThat(deletedOrder).isNotPresent();
    }
}