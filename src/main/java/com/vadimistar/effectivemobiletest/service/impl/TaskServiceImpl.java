package com.vadimistar.effectivemobiletest.service.impl;

import com.vadimistar.effectivemobiletest.dto.TaskDto;
import com.vadimistar.effectivemobiletest.dto.UpdateTaskDto;
import com.vadimistar.effectivemobiletest.entity.Task;
import com.vadimistar.effectivemobiletest.entity.User;
import com.vadimistar.effectivemobiletest.exception.TaskNotFoundException;
import com.vadimistar.effectivemobiletest.mapper.TaskMapper;
import com.vadimistar.effectivemobiletest.repository.TaskRepository;
import com.vadimistar.effectivemobiletest.service.TaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskDto getTask(User user, long taskId) {
        Task task = taskRepository.findByIdAndPerformerId(taskId, user.getId())
                .orElseThrow(() -> new TaskNotFoundException("Task with this id is not found: " + taskId));

        return taskMapper.mapTaskToTaskDto(task);
    }

    @Override
    public List<TaskDto> getTasks(User user, Pageable pageable) {
        List<Task> tasks = taskRepository.findByPerformerId(user.getId(), pageable);

        return tasks.stream()
                .map(taskMapper::mapTaskToTaskDto)
                .toList();
    }

    @Override
    @Transactional
    public TaskDto updateTask(User user, long taskId, UpdateTaskDto updateTaskDto) {
        Task task = taskRepository.findByIdAndPerformerId(taskId, user.getId())
                .orElseThrow(() -> new TaskNotFoundException("Task with this id is not found: " + taskId));

        taskMapper.updateTask(task, updateTaskDto);
        taskRepository.saveAndFlush(task);

        return taskMapper.mapTaskToTaskDto(task);
    }
}
