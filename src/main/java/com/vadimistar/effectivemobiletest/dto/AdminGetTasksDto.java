package com.vadimistar.effectivemobiletest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminGetTasksDto {

    private Long creatorId;
    private Long performerId;
}
