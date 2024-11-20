package com.vadimistar.effectivemobiletest.controller;

import com.vadimistar.effectivemobiletest.dto.CommentDto;
import com.vadimistar.effectivemobiletest.dto.CreateCommentDto;
import com.vadimistar.effectivemobiletest.entity.User;
import com.vadimistar.effectivemobiletest.service.CommentService;
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

@Tag(name = "Комментарии", description = "Управление комментариями для всех пользователей")
@RestController
@SecurityRequirement(name = "JWT")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Получить комментарии", description = "Получить комментарии под определенным задачей. Для этого пользователь должен быть исполнителем этой задачи")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список с комментариями", content = {
                    @Content(array = @ArraySchema(
                            schema = @Schema(implementation = CommentDto.class)
                    ), mediaType = "application/json")
            }),
    })
    @PageableParameter
    @GetMapping("/task/{taskId}/comments")
    public List<CommentDto> getComments(@AuthenticationPrincipal User user,
                                        @Parameter(description = "ID задачи") @PathVariable long taskId,
                                        @Parameter(hidden = true) Pageable pageable) {
        return commentService.getComments(user, taskId, pageable);
    }

    @Operation(summary = "Написать комментарий", description = "Написать комментарий под определенным задачей. Для этого пользователь должен быть исполнителем этой задачи")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Комментарий, который написал пользователь", content = {
                    @Content(schema = @Schema(implementation = CommentDto.class), mediaType = "application/json")
            }),
    })
    @PostMapping("/task/{taskId}/comment")
    public CommentDto createComment(@AuthenticationPrincipal User user,
                                    @Parameter(description = "ID задачи") @PathVariable long taskId,
                                    @Valid @RequestBody CreateCommentDto createCommentDto) {
        return commentService.createComment(user, taskId, createCommentDto);
    }
}
