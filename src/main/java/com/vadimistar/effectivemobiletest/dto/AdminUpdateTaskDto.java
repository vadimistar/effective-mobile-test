package com.vadimistar.effectivemobiletest.dto;

import com.vadimistar.effectivemobiletest.entity.domain.Priority;
import com.vadimistar.effectivemobiletest.entity.domain.Status;
import com.vadimistar.effectivemobiletest.util.NullOrNotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Запрос на изменение задания")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminUpdateTaskDto {

    @Schema(description = "Название")
    @NullOrNotBlank(message = "Название не может быть пустым")
    private String title;

    @Schema(description = "Описание")
    private String description;

    @Schema(description = "Статус: pending (ожидание), in_process (в процессе) или done (выполнено)")
    private Status status;

    @Schema(description = "Приоритет: high (высокий), medium (средний) или low (низкий)")
    private Priority priority;

    @Schema(description = "ID исполнителя")
    private Long performerId;
}
