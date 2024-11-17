package com.vadimistar.effectivemobiletest.service;

import com.vadimistar.effectivemobiletest.dto.TaskDto;
import com.vadimistar.effectivemobiletest.entity.User;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Optional<TaskDto> getTask(User user, long taskId);
    List<TaskDto> getTasks(User user);
}
