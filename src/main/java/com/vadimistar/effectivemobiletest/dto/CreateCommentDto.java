package com.vadimistar.effectivemobiletest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Запрос на создание комментария")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentDto {

    @Schema(description = "Текст комментария", example = "Комментарий", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Текст не может быть null")
    private String text;
}
