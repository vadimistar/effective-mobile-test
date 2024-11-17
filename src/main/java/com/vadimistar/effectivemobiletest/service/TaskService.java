package com.vadimistar.effectivemobiletest.service;

import com.vadimistar.effectivemobiletest.dto.TaskDto;
import com.vadimistar.effectivemobiletest.dto.UpdateTaskDto;
import com.vadimistar.effectivemobiletest.entity.User;

import java.util.List;

public interface TaskService {

    TaskDto getTask(User user, long taskId);
    List<TaskDto> getTasks(User user);
    TaskDto updateTask(User user, long taskId, UpdateTaskDto updateTaskDto);
}
