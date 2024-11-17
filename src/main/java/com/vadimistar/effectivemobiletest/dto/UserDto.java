package com.vadimistar.effectivemobiletest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "Модель пользователя")
@Data
@Builder
public class UserDto {

    @Schema(description = "ID")
    private long id;

    @Schema(description = "Email")
    private String email;
}
