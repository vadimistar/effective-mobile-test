package com.vadimistar.effectivemobiletest.dto;

import com.vadimistar.effectivemobiletest.entity.domain.Priority;
import com.vadimistar.effectivemobiletest.entity.domain.Status;
import com.vadimistar.effectivemobiletest.validation.NullOrNotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminUpdateTaskDto {

    @NullOrNotBlank(message = "Название не может быть пустым")
    private String title;

    private String description;
    private Status status;
    private Priority priority;
    private Long performerId;
}
