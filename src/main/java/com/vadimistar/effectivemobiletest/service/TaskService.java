package com.vadimistar.effectivemobiletest.service;

import com.vadimistar.effectivemobiletest.dto.TaskDto;
import com.vadimistar.effectivemobiletest.dto.UpdateTaskDto;
import com.vadimistar.effectivemobiletest.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {

    TaskDto getTask(User user, long taskId);
    List<TaskDto> getTasks(User user, Pageable pageable);
    TaskDto updateTask(User user, long taskId, UpdateTaskDto updateTaskDto);
}
