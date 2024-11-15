package com.vadimistar.effectivemobiletest.service;

import com.vadimistar.effectivemobiletest.dto.RegisterUserDto;
import com.vadimistar.effectivemobiletest.dto.UserDto;

public interface UserService {

    UserDto registerUser(RegisterUserDto registerUserDto);
}
