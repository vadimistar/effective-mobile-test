package com.vadimistar.effectivemobiletest.controller;

import com.vadimistar.effectivemobiletest.dto.AdminTaskDto;
import com.vadimistar.effectivemobiletest.service.AdminTaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
