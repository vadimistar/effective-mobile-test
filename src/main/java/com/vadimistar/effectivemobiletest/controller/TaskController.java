package com.vadimistar.effectivemobiletest.controller;

import com.vadimistar.effectivemobiletest.dto.ErrorDto;
import com.vadimistar.effectivemobiletest.dto.TaskDto;
import com.vadimistar.effectivemobiletest.dto.UpdateTaskDto;
import com.vadimistar.effectivemobiletest.entity.User;
import com.vadimistar.effectivemobiletest.service.TaskService;
import com.vadimistar.effectivemobiletest.util.PageableParameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Задачи", description = "Управление задачами для всех пользователей")
@SecurityRequirement(name = "JWT")
@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Получить задачу", description = "Получить задачу по ID. Для этого пользователь должен быть исполнителем этой задачи")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "задача", content = {
                    @Content(schema = @Schema(implementation = TaskDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "404", description = "задача не найдено", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = "application/json")
            }),
    })
    @GetMapping("/task/{id}")
    public TaskDto getTask(@AuthenticationPrincipal User user,
                           @Parameter(description = "ID задачи") @PathVariable long id) {
        return taskService.getTask(user, id);
    }

    @Operation(summary = "Получить задачи", description = "Получить задачи, которые исполняет пользователь")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список с задачами", content = {
                    @Content(array = @ArraySchema(
                            schema = @Schema(implementation = TaskDto.class)
                    ), mediaType = "application/json")
            }),
    })
    @PageableParameter
    @GetMapping("/tasks")
    public List<TaskDto> getTasks(@AuthenticationPrincipal User user,
                                  @Parameter(hidden = true) Pageable pageable) {
        return taskService.getTasks(user, pageable);
    }

    @Operation(summary = "Обновить задачу", description = "Обновить задачу по ID. Для этого пользователь должен быть исполнителем этой задачи")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Обновленная задача", content = {
                    @Content(schema = @Schema(implementation = TaskDto.class), mediaType = "application/json")
            }),
    })
    @PatchMapping("/task/{id}")
    public TaskDto updateTask(@AuthenticationPrincipal User user,
                              @Parameter(description = "ID задачи") @PathVariable long id,
                              @Valid @RequestBody UpdateTaskDto updateTaskDto) {
        return taskService.updateTask(user, id, updateTaskDto);
    }
}
