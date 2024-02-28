package com.sparta.deliveryproject.controller;


import com.sparta.deliveryproject.dto.CommonResponseDto;
import com.sparta.deliveryproject.dto.SignupRequestDto;
import com.sparta.deliveryproject.dto.UserResponseDto;
import com.sparta.deliveryproject.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/signup")
    public ResponseEntity<CommonResponseDto> signup(@RequestBody SignupRequestDto requestdto) {
        userService.signup(requestdto);
        return ResponseEntity.status(200).body(new CommonResponseDto(200, "회원가입 완료"));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/users")
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        List<UserResponseDto> userResponseDtoList = userService.getUsers();
        return ResponseEntity.status(200).body(userResponseDtoList);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/admin/user/{userId}")
    public ResponseEntity<CommonResponseDto> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(200).body(new CommonResponseDto(200, "유저 삭제 성공."));
    }
}
