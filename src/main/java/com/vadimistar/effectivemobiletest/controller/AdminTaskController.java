package com.vadimistar.effectivemobiletest.controller;

import com.vadimistar.effectivemobiletest.dto.*;
import com.vadimistar.effectivemobiletest.entity.User;
import com.vadimistar.effectivemobiletest.service.AdminTaskService;
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
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Задачи (админ)", description = "Управление задачами для админов")
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "JWT")
@RequestMapping("/admin")
public class AdminTaskController {

    private final AdminTaskService adminTaskService;

    @Operation(summary = "Получить задание", description = "Получить задание по ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задание", content = {
                    @Content(schema = @Schema(implementation = AdminTaskDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован, или неверный токен", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "404", description = "Задание не найдено", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "405", description = "Внутренняя ошибка сервера", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = "application/json")
            }),
    })
    @GetMapping("/task/{taskId}")
    public AdminTaskDto getTask(@Parameter(description = "ID задачи") @PathVariable long taskId) {
        return adminTaskService.getTask(taskId);
    }

    @Operation(summary = "Получить задания", description = "Получить задания по ID создателя и ID исполнителя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список с заданиями", content = {
                    @Content(array = @ArraySchema(
                            schema = @Schema(implementation = AdminTaskDto.class)
                    ), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован, или неверный токен", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "405", description = "Внутренняя ошибка сервера", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = "application/json")
            }),
    })
    @PageableParameter
    @GetMapping("/tasks")
    public List<AdminTaskDto> getTasks(@ParameterObject AdminGetTasksDto adminGetTasksDto,
                                       @Parameter(hidden = true) Pageable pageable) {
        return adminTaskService.getTasks(adminGetTasksDto, pageable);
    }

    @Operation(summary = "Создать задание")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Созданное задание", content = {
                    @Content(schema = @Schema(implementation = AdminTaskDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован, или неверный токен", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "405", description = "Внутренняя ошибка сервера", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = "application/json")
            }),
    })
    @PostMapping("/task")
    public AdminTaskDto createTask(@AuthenticationPrincipal User user,
                                   @Valid @RequestBody AdminCreateTaskDto adminCreateTaskDto) {
        return adminTaskService.createTask(user, adminCreateTaskDto);
    }

    @Operation(summary = "Изменить задание", description = "Изменить задание, возможно указать только определенные поля для изменения")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Измененное задание", content = {
                    @Content(schema = @Schema(implementation = AdminTaskDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован, или неверный токен", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "405", description = "Внутренняя ошибка сервера", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = "application/json")
            }),
    })
    @PatchMapping("/task/{id}")
    public AdminTaskDto updateTask(@Parameter(description = "ID задачи") @PathVariable long id,
                                   @Valid @RequestBody AdminUpdateTaskDto adminUpdateTaskDto) {
        return adminTaskService.updateTask(id, adminUpdateTaskDto);
    }

    @Operation(summary = "Удалить задание")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Удаленное задание", content = {
                    @Content(schema = @Schema(implementation = AdminTaskDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован, или неверный токен", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "405", description = "Внутренняя ошибка сервера", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class), mediaType = "application/json")
            }),
    })
    @DeleteMapping("/task/{id}")
    public AdminTaskDto deleteTask(@Parameter(description = "ID задания") @PathVariable long id) {
        return adminTaskService.deleteTask(id);
    }
}
