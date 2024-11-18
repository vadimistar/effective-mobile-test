package com.vadimistar.effectivemobiletest.controller;

import com.vadimistar.effectivemobiletest.dto.TaskDto;
import com.vadimistar.effectivemobiletest.dto.UpdateTaskDto;
import com.vadimistar.effectivemobiletest.entity.User;
import com.vadimistar.effectivemobiletest.service.TaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "JWT")
@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/task/{id}")
    public TaskDto getTask(@AuthenticationPrincipal User user, @PathVariable long id) {
        return taskService.getTask(user, id);
    }

    @GetMapping("/tasks")
    public List<TaskDto> getTasks(@AuthenticationPrincipal User user) {
        return taskService.getTasks(user);
    }

    @PatchMapping("/task/{id}")
    public TaskDto updateTask(@AuthenticationPrincipal User user,
                              @PathVariable long id,
                              @Valid @RequestBody UpdateTaskDto updateTaskDto) {
        return taskService.updateTask(user, id, updateTaskDto);
    }
}
