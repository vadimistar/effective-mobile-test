package com.vadimistar.effectivemobiletest.service.impl;

import com.vadimistar.effectivemobiletest.dto.JwtDto;
import com.vadimistar.effectivemobiletest.dto.LoginUserDto;
import com.vadimistar.effectivemobiletest.dto.RegisterUserDto;
import com.vadimistar.effectivemobiletest.dto.UserDto;
import com.vadimistar.effectivemobiletest.entity.User;
import com.vadimistar.effectivemobiletest.exception.EmailAlreadyExistsException;
import com.vadimistar.effectivemobiletest.mapper.UserMapper;
import com.vadimistar.effectivemobiletest.repository.UserRepository;
import com.vadimistar.effectivemobiletest.service.JwtService;
import com.vadimistar.effectivemobiletest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public UserDto registerUser(RegisterUserDto registerUserDto) {
        if (userRepository.existsByEmail(registerUserDto.getEmail())) {
            throw new EmailAlreadyExistsException("Пользователь с этим email уже существует");
        }

        User user = userMapper.mapRegisterUserDtoToUser(registerUserDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.saveAndFlush(user);

        return userMapper.mapUserToUserDto(user);
    }

    @Override
    public JwtDto loginUser(LoginUserDto loginUserDto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(), loginUserDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
        return jwtService.createToken(auth);
    }

    @Override
    public UserDto getUser(User user) {
        return userMapper.mapUserToUserDto(user);
    }
}
