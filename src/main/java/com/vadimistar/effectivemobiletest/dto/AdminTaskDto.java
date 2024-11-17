package com.vadimistar.effectivemobiletest.dto;

import com.vadimistar.effectivemobiletest.entity.domain.Priority;
import com.vadimistar.effectivemobiletest.entity.domain.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminTaskDto {

    private long id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private UserDto creator;
    private UserDto performer;
}
