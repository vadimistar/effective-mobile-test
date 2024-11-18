package com.vadimistar.effectivemobiletest.service;

import com.vadimistar.effectivemobiletest.dto.CommentDto;
import com.vadimistar.effectivemobiletest.dto.CreateCommentDto;
import com.vadimistar.effectivemobiletest.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminCommentService {

    List<CommentDto> getComments(long taskId, Pageable pageable);
    CommentDto createComment(User user, long taskId, CreateCommentDto createCommentDto);
}
