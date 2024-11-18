package com.vadimistar.effectivemobiletest.controller;

import com.vadimistar.effectivemobiletest.dto.AdminCreateTaskDto;
import com.vadimistar.effectivemobiletest.dto.AdminTaskDto;
import com.vadimistar.effectivemobiletest.dto.AdminGetTasksDto;
import com.vadimistar.effectivemobiletest.dto.AdminUpdateTaskDto;
import com.vadimistar.effectivemobiletest.entity.User;
import com.vadimistar.effectivemobiletest.service.AdminTaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "JWT")
@RequestMapping("/admin")
public class AdminTaskController {

    private final AdminTaskService adminTaskService;

    @GetMapping("/task/{id}")
    public AdminTaskDto getTask(@PathVariable long id) {
        return adminTaskService.getTask(id);
    }

    @GetMapping("/tasks")
    public List<AdminTaskDto> getTasks(AdminGetTasksDto adminGetTasksDto, Pageable pageable) {
        return adminTaskService.getTasks(adminGetTasksDto, pageable);
    }

    @PostMapping("/task")
    public AdminTaskDto createTask(@AuthenticationPrincipal User user,
                                   @Valid @RequestBody AdminCreateTaskDto adminCreateTaskDto) {
        return adminTaskService.createTask(user, adminCreateTaskDto);
    }

    @PatchMapping("/task/{id}")
    public AdminTaskDto updateTask(@PathVariable long id,
                                   @Valid @RequestBody AdminUpdateTaskDto adminUpdateTaskDto) {
        return adminTaskService.updateTask(id, adminUpdateTaskDto);
    }

    @DeleteMapping("/task/{id}")
    public AdminTaskDto deleteTask(@PathVariable long id) {
        return adminTaskService.deleteTask(id);
    }
}
