package com.vadimistar.effectivemobiletest.controller;

import com.vadimistar.effectivemobiletest.dto.TaskDto;
import com.vadimistar.effectivemobiletest.entity.User;
import com.vadimistar.effectivemobiletest.service.TaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@SecurityRequirement(name = "JWT")
@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/task/{id}")
    public ResponseEntity<?> getTask(@AuthenticationPrincipal User user, @PathVariable long id) {
        Optional<TaskDto> task = taskService.getTask(user, id);

        if (task.isPresent()) {
            return ResponseEntity.ok(task.get());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/tasks")
    public List<TaskDto> getTasks(@AuthenticationPrincipal User user) {
        return taskService.getTasks(user);
    }
}
