package com.vadimistar.effectivemobiletest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "Ошибка")
@Data
@AllArgsConstructor
public class ErrorDto {

    @Schema(description = "Сообщение с ошибкой")
    private String error;
}
