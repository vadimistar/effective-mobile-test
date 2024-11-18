package com.vadimistar.effectivemobiletest.service.impl;

import com.vadimistar.effectivemobiletest.dto.CommentDto;
import com.vadimistar.effectivemobiletest.dto.CreateCommentDto;
import com.vadimistar.effectivemobiletest.entity.Comment;
import com.vadimistar.effectivemobiletest.entity.Task;
import com.vadimistar.effectivemobiletest.entity.User;
import com.vadimistar.effectivemobiletest.exception.InvalidTaskIdException;
import com.vadimistar.effectivemobiletest.mapper.CommentMapper;
import com.vadimistar.effectivemobiletest.repository.CommentRepository;
import com.vadimistar.effectivemobiletest.repository.TaskRepository;
import com.vadimistar.effectivemobiletest.service.AdminCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCommentServiceImpl implements AdminCommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final CommentMapper commentMapper;

    @Override
    public List<CommentDto> getComments(long taskId, Pageable pageable) {
        List<Comment> comments = commentRepository.findByTaskId(taskId, pageable);

        return comments.stream()
                .map(commentMapper::mapCommentToCommentDto)
                .toList();
    }

    @Override
    public CommentDto createComment(User user, long taskId, CreateCommentDto createCommentDto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new InvalidTaskIdException("Неверный ID задачи: " + taskId));

        Comment comment = commentMapper.mapCreateCommentDtoToComment(createCommentDto);

        comment.setTask(task);
        comment.setAuthor(user);

        return commentMapper.mapCommentToCommentDto(comment);
    }
}
