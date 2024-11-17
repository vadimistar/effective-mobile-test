package com.vadimistar.effectivemobiletest.service.impl;

import com.vadimistar.effectivemobiletest.dto.TaskDto;
import com.vadimistar.effectivemobiletest.entity.Task;
import com.vadimistar.effectivemobiletest.entity.User;
import com.vadimistar.effectivemobiletest.mapper.TaskMapper;
import com.vadimistar.effectivemobiletest.repository.TaskRepository;
import com.vadimistar.effectivemobiletest.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public Optional<TaskDto> getTask(User user, long taskId) {
        Optional<Task> task = taskRepository.findByIdAndPerformerId(taskId, user.getId());

        return task.map(taskMapper::mapTaskToTaskDto);
    }

    @Override
    public List<TaskDto> getTasks(User user) {
        List<Task> tasks = taskRepository.findByPerformerId(user.getId());

        return tasks.stream()
                .map(taskMapper::mapTaskToTaskDto)
                .toList();
    }
}
