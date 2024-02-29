package com.sparta.deliveryproject.resolver;

import com.sparta.deliveryproject.annotation.CurrentUser;
import com.sparta.deliveryproject.entity.User;
import com.sparta.deliveryproject.repository.UserRepository;
import com.sparta.deliveryproject.service.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public CurrentUserArgumentResolver(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(CurrentUser.class) != null && parameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String token = jwtUtil.getTokenFromRequest(request);
        Claims userInfo = jwtUtil.getUserInfoFromToken(token);
        User user = userRepository.findByUsername(userInfo.getSubject()).orElseThrow(() ->
                new NullPointerException("Not Found User")
        );
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println(user);
        return user;
    }
}
