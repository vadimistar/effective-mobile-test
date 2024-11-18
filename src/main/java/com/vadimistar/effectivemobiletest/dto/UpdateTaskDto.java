package com.vadimistar.effectivemobiletest.dto;

import com.vadimistar.effectivemobiletest.entity.domain.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Запрос на обновление задания")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaskDto {

    @Schema(description = "Статус: pending (ожидание), in_process (в процессе) или done (выполнено)")
    private Status status;
}
