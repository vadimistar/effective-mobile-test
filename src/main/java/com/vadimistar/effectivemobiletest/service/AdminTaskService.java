package com.vadimistar.effectivemobiletest.service;

import com.vadimistar.effectivemobiletest.dto.AdminCreateTaskDto;
import com.vadimistar.effectivemobiletest.dto.AdminGetTasksDto;
import com.vadimistar.effectivemobiletest.dto.AdminTaskDto;
import com.vadimistar.effectivemobiletest.entity.User;

import java.util.List;

public interface AdminTaskService {

    AdminTaskDto getTask(long taskId);
    List<AdminTaskDto> getTasks(AdminGetTasksDto adminGetTasksDto);
    AdminTaskDto createTask(User user, AdminCreateTaskDto adminCreateTaskDto);
    AdminTaskDto deleteTask(long taskId);
}
