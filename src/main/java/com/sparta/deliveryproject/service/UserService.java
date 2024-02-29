package com.sparta.deliveryproject.service;


import com.sparta.deliveryproject.dto.SignupRequestDto;
import com.sparta.deliveryproject.dto.UserResponseDto;
import com.sparta.deliveryproject.entity.User;
import com.sparta.deliveryproject.entity.UserRoleEnum;
import com.sparta.deliveryproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    String ADMIN_TOKEN = "2";
    String ENTRE_TOKEN = "6";

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String address = requestDto.getAddress();

        Optional<User> checkUsername = userRepository.findByUsername(username);

        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        UserRoleEnum role = UserRoleEnum.USER;


        if (ADMIN_TOKEN.equals(requestDto.getAuthorityToken())) {
            role = UserRoleEnum.ADMIN;
        } else if (ENTRE_TOKEN.equals(requestDto.getAuthorityToken())) {

            role = UserRoleEnum.ENTRE;
        }

        User user = new User(username, password, address, role);
        userRepository.save(user);
    }


    public List<UserResponseDto> getUsers() {
        return userRepository.findAll().stream().map(UserResponseDto::new).toList();
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NullPointerException("해당 Id를 가진 유저는 존재하지 않습니다.")
        );
        if (Objects.equals(user.getAuthorities().toString(), "[ADMIN]")) {
            throw new IllegalArgumentException("admin 권한의 유저를 삭제할 순 없습니다.");
        }
        userRepository.deleteById(userId);
    }
}