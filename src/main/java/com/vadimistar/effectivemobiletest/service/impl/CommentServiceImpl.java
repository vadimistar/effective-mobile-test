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
import com.vadimistar.effectivemobiletest.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final TaskRepository taskRepository;

    @Override
    public List<CommentDto> getComments(User user, long taskId, Pageable pageable) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new InvalidTaskIdException("Неверный ID задачи: " + taskId));

        if (task.getPerformer() == null || !Objects.equals(task.getPerformer().getId(), user.getId())) {
            throw new InvalidTaskIdException("Неверный ID задачи: " + taskId);
        }

        List<Comment> comments = commentRepository.findByTaskId(taskId, pageable);

        return comments.stream()
                .map(commentMapper::mapCommentToCommentDto)
                .toList();
    }

    @Override
    public CommentDto createComment(User user, long taskId, CreateCommentDto createCommentDto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new InvalidTaskIdException("Неверный ID задачи: " + taskId));

        if (task.getPerformer() == null || !Objects.equals(task.getPerformer().getId(), user.getId())) {
            throw new InvalidTaskIdException("Неверный ID задачи: " + taskId);
        }

        Comment comment = commentMapper.mapCreateCommentDtoToComment(createCommentDto);
        comment.setAuthor(user);
        comment.setTask(task);

        commentRepository.saveAndFlush(comment);

        return commentMapper.mapCommentToCommentDto(comment);
    }
}