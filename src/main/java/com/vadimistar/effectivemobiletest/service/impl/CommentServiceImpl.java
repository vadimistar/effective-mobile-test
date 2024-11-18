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
        Task task = getTask(user, taskId);

        List<Comment> comments = commentRepository.findByTaskId(task.getId(), pageable);

        return comments.stream()
                .map(commentMapper::mapCommentToCommentDto)
                .toList();
    }

    @Override
    public CommentDto createComment(User user, long taskId, CreateCommentDto createCommentDto) {
        Task task = getTask(user, taskId);

        Comment comment = commentMapper.mapCreateCommentDtoToComment(createCommentDto);
        comment.setAuthor(user);
        comment.setTask(task);

        commentRepository.saveAndFlush(comment);

        return commentMapper.mapCommentToCommentDto(comment);
    }

    private Task getTask(User performer, long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new InvalidTaskIdException("Неверный ID задачи: " + id));

        if (task.getPerformer() == null || !Objects.equals(task.getPerformer().getId(), performer.getId())) {
            throw new InvalidTaskIdException("Неверный ID задачи: " + id);
        }

        return task;
    }
}
