package com.vadimistar.effectivemobiletest.controller;

import com.vadimistar.effectivemobiletest.dto.AdminTaskDto;
import com.vadimistar.effectivemobiletest.dto.AdminGetTasksDto;
import com.vadimistar.effectivemobiletest.service.AdminTaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
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
    public List<AdminTaskDto> getTasks(AdminGetTasksDto adminGetTasksDto) {
        return adminTaskService.getTasks(adminGetTasksDto);
    }

    @DeleteMapping("/task/{id}")
    public AdminTaskDto deleteTask(@PathVariable long id) {
        return adminTaskService.deleteTask(id);
    }
}
