package com.vadimistar.effectivemobiletest.service.impl;

import com.vadimistar.effectivemobiletest.dto.AdminTaskDto;
import com.vadimistar.effectivemobiletest.entity.Task;
import com.vadimistar.effectivemobiletest.exception.TaskNotFoundException;
import com.vadimistar.effectivemobiletest.mapper.AdminTaskMapper;
import com.vadimistar.effectivemobiletest.repository.TaskRepository;
import com.vadimistar.effectivemobiletest.service.AdminTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminTaskServiceImpl implements AdminTaskService {

    private final TaskRepository taskRepository;
    private final AdminTaskMapper adminTaskMapper;

    @Override
    public AdminTaskDto getTask(long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task with this id is not found: " + taskId));

        return adminTaskMapper.mapTaskToAdminTaskDto(task);
    }

    @Override
    public AdminTaskDto deleteTask(long taskId) {
        AdminTaskDto adminTaskDto = getTask(taskId);

        taskRepository.deleteById(taskId);

        return adminTaskDto;
    }
}
