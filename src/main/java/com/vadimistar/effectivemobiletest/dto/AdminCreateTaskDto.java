package com.vadimistar.effectivemobiletest.dto;

import com.vadimistar.effectivemobiletest.entity.domain.Priority;
import com.vadimistar.effectivemobiletest.entity.domain.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Schema(description = "Запрос на создание задания")
@Data
@Builder
public class AdminCreateTaskDto {

    @Schema(description = "Название")
    @NotBlank(message = "Название не может быть пустым")
    private String title;

    @Schema(description = "Описание")
    @NotNull(message = "Описание не может быть null")
    private String description;

    @Schema(description = "Статус: pending (ожидание), in_process (в процессе) или done (выполнено)")
    private Status status;

    @Schema(description = "Приоритет: high (высокий), medium (средний) или low (низкий)")
    private Priority priority;

    @Schema(description = "ID исполнителя (необязателен)")
    private Long performerId;
}
