package com.vadimistar.effectivemobiletest.controller;

import com.vadimistar.effectivemobiletest.dto.RegisterUserDto;
import com.vadimistar.effectivemobiletest.dto.UserDto;
import com.vadimistar.effectivemobiletest.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public UserDto registerUser(@Valid @RequestBody RegisterUserDto registerUserDto) {
        return userService.registerUser(registerUserDto);
    }
}
