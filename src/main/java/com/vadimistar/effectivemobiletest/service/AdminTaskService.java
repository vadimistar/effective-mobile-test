package com.vadimistar.effectivemobiletest.service;

import com.vadimistar.effectivemobiletest.dto.AdminCreateTaskDto;
import com.vadimistar.effectivemobiletest.dto.AdminGetTasksDto;
import com.vadimistar.effectivemobiletest.dto.AdminTaskDto;
import com.vadimistar.effectivemobiletest.dto.AdminUpdateTaskDto;
import com.vadimistar.effectivemobiletest.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminTaskService {

    AdminTaskDto getTask(long taskId);
    List<AdminTaskDto> getTasks(AdminGetTasksDto adminGetTasksDto, Pageable pageable);
    AdminTaskDto createTask(User user, AdminCreateTaskDto adminCreateTaskDto);
    AdminTaskDto updateTask(long id, AdminUpdateTaskDto adminUpdateTaskDto);
    AdminTaskDto deleteTask(long taskId);
}
