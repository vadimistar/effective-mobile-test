package com.vadimistar.effectivemobiletest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Schema(description = "Запрос на регистрацию пользователя")
@Data
@Builder
public class RegisterUserDto {

    @Schema(description = "Email")
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Неверный email")
    private String email;

    @Schema(description = "Пароль, длинной не менее 4 символов")
    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 4, message = "Пароль не может быть меньше 4 символов")
    private String password;
}
