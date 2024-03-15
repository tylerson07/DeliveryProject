package com.sparta.deliveryproject.entity;

import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrdersTest {

    @Autowired
    private TestEntityManager entityManager;

    Store store;
    Menu menu;
    Orders orders;

    @BeforeEach
    public void setUp(){
        store = new Store();
        store.setName("test");
        store.setIntroduce("test");
        store.setCategory(CategoryEnum.KOREAN);
        store.setAddress("test");

        menu = new Menu();
        menu.setName("치킨");
        menu.setPrice(20000);
        menu.setStore(store);
        menu.setIntroduce("양념 치킨");
    }

    @Test
    @DisplayName("Orders 엔티티 생성 - 성공")
    public void CreateOrdersSuccess(){
        // given
        entityManager.persist(store);
        entityManager.persist(menu);

        Orders orders = new Orders();
        orders.setMenu(menu);
        orders.setQuantity(1L);
        orders.setTotalPrice(20000L);

        // when
        Orders savedOrders = entityManager.persistFlushFind(orders);

        // then
        assertNotNull(savedOrders);
        assertEquals(savedOrders.getMenu().getName(), menu.getName());
        assertEquals(savedOrders.getTotalPrice(), 20000L);
    }

    @Test
    @DisplayName("Orders 엔티티 생성 - 실패(quantity : null)")
    public void CreateOrdersFail(){
        // given
        entityManager.persist(store);
        entityManager.persist(menu);

        Orders orders = new Orders();
        orders.setMenu(menu);
        orders.setQuantity(null);
        orders.setTotalPrice(20000L);

        // when - then
        assertThrows(PersistenceException.class, () -> {
            entityManager.persist(orders);
            entityManager.flush(); // 실제 데이터베이스에 쓰기를 시도하여 제약 조건 검증
        });

    }

}