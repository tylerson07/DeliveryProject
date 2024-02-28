package com.sparta.deliveryproject.controller;


import com.sparta.deliveryproject.dto.CommonResponseDto;
import com.sparta.deliveryproject.dto.SignupRequestDto;
import com.sparta.deliveryproject.dto.UserResponseDto;
import com.sparta.deliveryproject.entity.User;
import com.sparta.deliveryproject.repository.UserRepository;
import com.sparta.deliveryproject.security.UserDetailsImpl;
import com.sparta.deliveryproject.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
@RestController
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    PasswordEncoder passwordEncoder;
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
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

    @PutMapping("/user/changepass")
    public User changePassword(@RequestBody String password, @AuthenticationPrincipal UserDetailsImpl userDetails){
        String username = userDetails.getUsername();
        String encodedPassword = passwordEncoder.encode(password);
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.get();
        user.changePassword(encodedPassword);
        return userRepository.save(user);
    }
}
