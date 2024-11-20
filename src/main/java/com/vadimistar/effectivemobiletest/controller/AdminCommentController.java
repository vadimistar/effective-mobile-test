package com.vadimistar.effectivemobiletest.controller;

import com.vadimistar.effectivemobiletest.dto.CommentDto;
import com.vadimistar.effectivemobiletest.dto.CreateCommentDto;
import com.vadimistar.effectivemobiletest.entity.User;
import com.vadimistar.effectivemobiletest.service.AdminCommentService;
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

@Tag(name = "Комментарии (админ)", description = "Управление комментариями для админов")
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "JWT")
@RequestMapping("/admin")
public class AdminCommentController {

    private final AdminCommentService adminCommentService;

    @Operation(summary = "Получить комментарии", description = "Получить комментарии под определенным задачей")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список с комментариями", content = {
                    @Content(array = @ArraySchema(
                            schema = @Schema(implementation = CommentDto.class)
                    ), mediaType = "application/json")
            }),
    })
    @PageableParameter
    @GetMapping("/task/{taskId}/comments")
    public List<CommentDto> getComments(@Parameter(description = "ID задачи") @PathVariable long taskId,
                                        @Parameter(hidden = true) Pageable pageable) {
        return adminCommentService.getComments(taskId, pageable);
    }

    @Operation(summary = "Написать комментарий", description = "Написать комментарий под определенным задачей")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Комментарий, который написал пользователь", content = {
                    @Content(schema = @Schema(implementation = CommentDto.class), mediaType = "application/json")
            }),
    })
    @PostMapping("/task/{taskId}/comment")
    public CommentDto createComment(@AuthenticationPrincipal User user,
                                    @Parameter(description = "ID задачи") @PathVariable long taskId,
                                    @Valid @RequestBody CreateCommentDto createCommentDto) {
        return adminCommentService.createComment(user, taskId, createCommentDto);
    }
}
