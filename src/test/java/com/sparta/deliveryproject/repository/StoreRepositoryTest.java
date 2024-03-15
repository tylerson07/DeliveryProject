package com.sparta.deliveryproject.repository;

import com.sparta.deliveryproject.dto.StoreRequestDto;
import com.sparta.deliveryproject.entity.Store;
import com.sparta.deliveryproject.entity.User;
import com.sparta.deliveryproject.entity.UserRoleEnum;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StoreRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @Before
    public void setUp() {
        UserRoleEnum userRoleEnum = UserRoleEnum.USER;
        user = new User("test", "test", "test", userRoleEnum);
        entityManager.persist(user);

        StoreRequestDto storeRequestDto = new StoreRequestDto();
        storeRequestDto.setCategory("CHINESE");
        storeRequestDto.setIntroduce("건대 유명 중국음식점");
        storeRequestDto.setAddress("서울시 광진구");
        storeRequestDto.setName("시옌");
        Store store = new Store(storeRequestDto, user);
        entityManager.persist(store);
    }

    @After
    public void tearDown() throws Exception {
        entityManager.clear();
    }

    @Test
    @DisplayName("findById")
    void findByUser_test(){
        Optional<Store> store1 = storeRepository.findById(1L);
        assertThat(store1).isPresent();
    }

}