package com.vadimistar.effectivemobiletest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginUserDto {

    @NotBlank(message = "Email не может быть пустым")
    private String email;

    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}
