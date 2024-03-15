package com.sparta.deliveryproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.deliveryproject.config.WebSecurityConfig;
import com.sparta.deliveryproject.dto.StoreRequestDto;
import com.sparta.deliveryproject.entity.User;
import com.sparta.deliveryproject.entity.UserRoleEnum;
import com.sparta.deliveryproject.filter.MockSpringSecurityFilter;
import com.sparta.deliveryproject.security.UserDetailsImpl;
import com.sparta.deliveryproject.service.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StoreController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        }
)
class StoreControllerTest {

    private MockMvc mvc;

    private Principal principal;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StoreService storeService;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();
    }

    private void mockUserAdminSetup() {
        String username = "testUserAdmin";
        String password = "testUserAdmin";
        String address = "testUserAdmin";
        UserRoleEnum role = UserRoleEnum.ADMIN;

        User testUserAdmin = new User(username, password, address, role);
        UserDetailsImpl userDetails = new UserDetailsImpl(testUserAdmin);
        principal = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    @Test
    @DisplayName("postStore 성공")
    void postStore_success() throws Exception {
        this.mockUserAdminSetup();

        StoreRequestDto storeRequestDto = new StoreRequestDto();
        String storeJson = objectMapper.writeValueAsString(storeRequestDto);

        mvc.perform(post("/api/store")
                        .content(storeJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(principal)
                )
                .andExpect(status().is(200))
                .andDo(print());
    }
}