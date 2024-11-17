package com.vadimistar.effectivemobiletest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUserDto {

    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Неверный email")
    private String email;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 4, message = "Пароль не может быть меньше 4 символов")
    private String password;
}
