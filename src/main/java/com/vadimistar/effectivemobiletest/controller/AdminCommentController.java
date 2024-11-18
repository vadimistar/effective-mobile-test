package com.vadimistar.effectivemobiletest.controller;

import com.vadimistar.effectivemobiletest.dto.CommentDto;
import com.vadimistar.effectivemobiletest.dto.CreateCommentDto;
import com.vadimistar.effectivemobiletest.entity.User;
import com.vadimistar.effectivemobiletest.service.AdminCommentService;
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
public class AdminCommentController {

    private final AdminCommentService adminCommentService;

    @GetMapping("/task/{id}/comments")
    public List<CommentDto> getComments(@PathVariable long id,
                                        Pageable pageable) {
        return adminCommentService.getComments(id, pageable);
    }

    @PostMapping("/task/{id}/comment")
    public CommentDto createComment(@AuthenticationPrincipal User user,
                                    @PathVariable long id,
                                    @Valid @RequestBody CreateCommentDto createCommentDto) {
        return adminCommentService.createComment(user, id, createCommentDto);
    }
}
