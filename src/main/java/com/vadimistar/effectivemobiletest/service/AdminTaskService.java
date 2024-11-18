package com.vadimistar.effectivemobiletest.service;

import com.vadimistar.effectivemobiletest.dto.AdminGetTasksDto;
import com.vadimistar.effectivemobiletest.dto.AdminTaskDto;

import java.util.List;

public interface AdminTaskService {

    AdminTaskDto getTask(long taskId);
    List<AdminTaskDto> getTasks(AdminGetTasksDto adminGetTasksDto);
    AdminTaskDto deleteTask(long taskId);
}
