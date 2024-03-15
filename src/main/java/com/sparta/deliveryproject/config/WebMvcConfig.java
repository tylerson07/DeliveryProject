package com.sparta.deliveryproject.config;

import com.sparta.deliveryproject.repository.UserRepository;
import com.sparta.deliveryproject.resolver.CurrentUserArgumentResolver;
import com.sparta.deliveryproject.service.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new CurrentUserArgumentResolver(jwtUtil, userRepository));
    }
}
