package com.vadimistar.effectivemobiletest.service.impl;

import com.vadimistar.effectivemobiletest.dto.AdminCreateTaskDto;
import com.vadimistar.effectivemobiletest.dto.AdminGetTasksDto;
import com.vadimistar.effectivemobiletest.dto.AdminTaskDto;
import com.vadimistar.effectivemobiletest.dto.AdminUpdateTaskDto;
import com.vadimistar.effectivemobiletest.entity.Task;
import com.vadimistar.effectivemobiletest.entity.User;
import com.vadimistar.effectivemobiletest.exception.InvalidPerformerIdException;
import com.vadimistar.effectivemobiletest.exception.TaskNotFoundException;
import com.vadimistar.effectivemobiletest.mapper.AdminTaskMapper;
import com.vadimistar.effectivemobiletest.repository.TaskRepository;
import com.vadimistar.effectivemobiletest.repository.UserRepository;
import com.vadimistar.effectivemobiletest.service.AdminTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminTaskServiceImpl implements AdminTaskService {

    private final TaskRepository taskRepository;
    private final AdminTaskMapper adminTaskMapper;
    private final UserRepository userRepository;

    @Override
    public AdminTaskDto getTask(long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task with this id is not found: " + taskId));

        return adminTaskMapper.mapTaskToAdminTaskDto(task);
    }

    @Override
    public List<AdminTaskDto> getTasks(AdminGetTasksDto adminGetTasksDto, Pageable pageable) {
        List<Task> tasks = taskRepository.findByCreatorIdAndPerformerId(
                adminGetTasksDto.getCreatorId(),
                adminGetTasksDto.getPerformerId(),
                pageable
        );

        return tasks.stream()
                .map(adminTaskMapper::mapTaskToAdminTaskDto)
                .toList();
    }

    @Override
    public AdminTaskDto createTask(User user, AdminCreateTaskDto adminCreateTaskDto) {
        Task task = adminTaskMapper.mapAdminCreateTaskDtoToTask(adminCreateTaskDto);

        task.setCreator(user);
        updateTaskPerformer(adminCreateTaskDto.getPerformerId(), task);

        taskRepository.saveAndFlush(task);

        return adminTaskMapper.mapTaskToAdminTaskDto(task);
    }

    @Override
    public AdminTaskDto updateTask(long taskId, AdminUpdateTaskDto adminUpdateTaskDto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task with this id is not found: " + taskId));

        adminTaskMapper.updateTask(task, adminUpdateTaskDto);
        updateTaskPerformer(adminUpdateTaskDto.getPerformerId(), task);

        task = taskRepository.saveAndFlush(task);

        return adminTaskMapper.mapTaskToAdminTaskDto(task);
    }

    @Override
    public AdminTaskDto deleteTask(long taskId) {
        AdminTaskDto adminTaskDto = getTask(taskId);

        taskRepository.deleteById(taskId);

        return adminTaskDto;
    }

    private void updateTaskPerformer(Long performerId, Task task) {
        if (performerId != null) {
            User performer = userRepository.findById(performerId)
                    .orElseThrow(() -> new InvalidPerformerIdException(
                            "Неправильный ID исполнителя: " + performerId
                    ));

            task.setPerformer(performer);
        }
    }
}
