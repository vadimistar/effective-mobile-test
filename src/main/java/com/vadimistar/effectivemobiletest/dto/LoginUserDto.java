package com.vadimistar.effectivemobiletest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Schema(description = "Запрос на авторизацию пользователя")
@Data
@Builder
public class LoginUserDto {

    @Schema(description = "Email")
    @NotBlank(message = "Email не может быть пустым")
    private String email;

    @Schema(description = "Пароль")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}
