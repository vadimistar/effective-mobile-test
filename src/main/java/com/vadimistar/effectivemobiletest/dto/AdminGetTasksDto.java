package com.vadimistar.effectivemobiletest.dto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminGetTasksDto {

    @Parameter(description = "ID создателя")
    private Long creatorId;

    @Parameter(description = "ID исполнителя")
    private Long performerId;
}
