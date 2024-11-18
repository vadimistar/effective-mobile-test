package com.vadimistar.effectivemobiletest.controller;

import com.vadimistar.effectivemobiletest.dto.CommentDto;
import com.vadimistar.effectivemobiletest.dto.CreateCommentDto;
import com.vadimistar.effectivemobiletest.entity.User;
import com.vadimistar.effectivemobiletest.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "JWT")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/task/{id}/comments")
    public List<CommentDto> getComments(@AuthenticationPrincipal User user,
                                        @PathVariable long id,
                                        Pageable pageable) {
        return commentService.getComments(user, id, pageable);
    }

    @PostMapping("/task/{id}/comment")
    public CommentDto createComment(@AuthenticationPrincipal User user,
                                    @PathVariable long id,
                                    @Valid @RequestBody CreateCommentDto createCommentDto) {
        return commentService.createComment(user, id, createCommentDto);
    }
}
