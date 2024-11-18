package com.vadimistar.effectivemobiletest.dto;

import com.vadimistar.effectivemobiletest.entity.domain.Priority;
import com.vadimistar.effectivemobiletest.entity.domain.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminCreateTaskDto {

    @NotBlank(message = "Название не может быть пустым")
    private String title;

    @NotNull(message = "Описание не может быть null")
    private String description;

    private Status status;
    private Priority priority;
    private Long performerId;
}
