package com.vadimistar.effectivemobiletest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "JWT токен")
@Data
@Builder
public class JwtDto {

    @Schema(description = "JWT токен")
    private String token;

    @Schema(description = "Время в секундах, в течение которого токен будет валиден")
    private int expiresInSeconds;
}
