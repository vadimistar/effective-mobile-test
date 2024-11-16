package com.vadimistar.effectivemobiletest.service;

import com.vadimistar.effectivemobiletest.dto.JwtDto;
import com.vadimistar.effectivemobiletest.dto.LoginUserDto;
import com.vadimistar.effectivemobiletest.dto.RegisterUserDto;
import com.vadimistar.effectivemobiletest.dto.UserDto;

public interface UserService {

    UserDto registerUser(RegisterUserDto registerUserDto);
    JwtDto loginUser(LoginUserDto loginUserDto);
}
