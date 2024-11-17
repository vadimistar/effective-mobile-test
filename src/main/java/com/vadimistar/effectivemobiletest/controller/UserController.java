package com.vadimistar.effectivemobiletest.controller;

import com.vadimistar.effectivemobiletest.dto.*;
import com.vadimistar.effectivemobiletest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Пользователи", description = "Регистрация, авторизация и идентификация пользователей")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Регистрация пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Регистрация прошла успешно", content = {
                    @Content(schema = @Schema(implementation = UserDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Неверный формат запроса", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "405", description = "Внутренняя ошибка сервера", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "409", description = "Пользователь с таким email уже существует", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = "application/json")
            })
    })
    @PostMapping("/user")
    public UserDto registerUser(@Valid @RequestBody RegisterUserDto registerUserDto) {
        return userService.registerUser(registerUserDto);
    }

    @Operation(summary = "Авторизация пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Авторизация прошла успешно", content = {
                    @Content(schema = @Schema(implementation = JwtDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Неверный формат запроса", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "405", description = "Внутренняя ошибка сервера", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = "application/json")
            }),
    })
    @PostMapping("/auth/login")
    public JwtDto loginUser(@Valid @RequestBody LoginUserDto loginUserDto) {
        return userService.loginUser(loginUserDto);
    }
}
