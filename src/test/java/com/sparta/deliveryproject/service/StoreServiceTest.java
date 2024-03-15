package com.sparta.deliveryproject.service;

import com.sparta.deliveryproject.dto.StoreRequestDto;
import com.sparta.deliveryproject.entity.Store;
import com.sparta.deliveryproject.entity.User;
import com.sparta.deliveryproject.entity.UserRoleEnum;
import com.sparta.deliveryproject.repository.CategoryRepository;
import com.sparta.deliveryproject.repository.StoreRepository;
import com.sparta.deliveryproject.security.UserDetailsImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @Mock
    StoreRepository storeRepository;

    @Mock
    CategoryRepository categoryRepository;

    private UserDetailsImpl UserAdminSetup() {
        String username = "testUserAdmin";
        String password = "testUserAdmin";
        String address = "testUserAdmin";
        UserRoleEnum role = UserRoleEnum.ADMIN;

        User testUserAdmin = new User(username, password, address, role);
        return new UserDetailsImpl(testUserAdmin);
    }

    @Nested
    @DisplayName("createStore 테스트")
    class createStoreTest {

        @Test
        @DisplayName("createStore 성공")
        void createStoreTest_success() {
            UserDetailsImpl userDetails = UserAdminSetup();

            StoreRequestDto storeRequestDto = new StoreRequestDto();
            storeRequestDto.setCategory("CHINESE");
            storeRequestDto.setIntroduce("건대 유명 중국음식점");
            storeRequestDto.setAddress("서울시 광진구");
            storeRequestDto.setName("시옌");

            StoreService storeService = new StoreService(storeRepository, categoryRepository);
            storeService.createStore(storeRequestDto, userDetails.getUser());
        }


        @Test
        @DisplayName("createStore 실패")
        void createStoreTest_fail() {
            UserDetailsImpl userDetails = UserAdminSetup();

            StoreRequestDto storeRequestDto = new StoreRequestDto();
            storeRequestDto.setCategory("CHINESsE");
            storeRequestDto.setIntroduce("건대 유명 중국음식점");
            storeRequestDto.setAddress("서울시 광진구");
            storeRequestDto.setName("시옌");

            StoreService storeService = new StoreService(storeRepository, categoryRepository);

            Exception exception = assertThrows(IllegalArgumentException.class, ()-> {
                storeService.createStore(storeRequestDto, userDetails.getUser());
            });

            assertEquals(
                    "존재하지 않는 카테고리입니다.",
                    exception.getMessage()
            );
        }
    }
}