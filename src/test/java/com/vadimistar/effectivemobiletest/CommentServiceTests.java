package com.vadimistar.effectivemobiletest;

import com.vadimistar.effectivemobiletest.dto.CommentDto;
import com.vadimistar.effectivemobiletest.dto.CreateCommentDto;
import com.vadimistar.effectivemobiletest.entity.Comment;
import com.vadimistar.effectivemobiletest.entity.Task;
import com.vadimistar.effectivemobiletest.entity.User;
import com.vadimistar.effectivemobiletest.exception.InvalidTaskIdException;
import com.vadimistar.effectivemobiletest.mapper.CommentMapper;
import com.vadimistar.effectivemobiletest.repository.CommentRepository;
import com.vadimistar.effectivemobiletest.repository.TaskRepository;
import com.vadimistar.effectivemobiletest.repository.UserRepository;
import com.vadimistar.effectivemobiletest.service.CommentService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static com.vadimistar.effectivemobiletest.TestUtils.*;

@SpringBootTest
@Testcontainers
public class CommentServiceTests {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentMapper commentMapper;

    @Container
    private static final MySQLContainer<?> mysqlContainer = createMySQLContainer();

    @AfterAll
    public static void cleanUp() {
        mysqlContainer.close();
    }

    @DynamicPropertySource
    public static void mysqlProperties(DynamicPropertyRegistry registry) {
        fillMySQLProperties(registry, mysqlContainer);
    }

    @BeforeEach
    public void beforeEach() {
        commentRepository.deleteAll();
        taskRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void getComments_invalidTaskId_throwsInvalidTaskIdException() {
        User user = createTestUser();

        userRepository.saveAndFlush(user);

        Assertions.assertThrows(
                InvalidTaskIdException.class,
                () -> commentService.getComments(user, 1, Pageable.unpaged())
        );
    }

    @Test
    public void getComments_userNotPerformsTask_throwsInvalidTaskIdException() {
        User user = createTestUser();
        Task task = createTestTask(user, null);

        userRepository.saveAndFlush(user);
        taskRepository.saveAndFlush(task);

        Assertions.assertThrows(
                InvalidTaskIdException.class,
                () -> commentService.getComments(user, task.getId(), Pageable.unpaged())
        );
    }

    @Test
    @Transactional
    public void getComments_createComments_returnsAllComments() {
        User user = createTestUser();
        User user2 = createTestUser();
        Task task = createTestTask(user, user);

        userRepository.saveAndFlush(user);
        userRepository.saveAndFlush(user2);
        taskRepository.saveAndFlush(task);

        List<Comment> comments = List.of(
                createTestComment(user, task),
                createTestComment(user2, task)
        );

        commentRepository.saveAllAndFlush(comments);

        List<CommentDto> expected = comments.stream()
                .map(commentMapper::mapCommentToCommentDto)
                .toList();

        Assertions.assertIterableEquals(
                expected,
                commentService.getComments(user, task.getId(), Pageable.unpaged())
        );
    }

    @Test
    public void createComment_invalidTaskId_throwsInvalidTaskIdException() {
        User user = createTestUser();

        userRepository.saveAndFlush(user);

        Assertions.assertThrows(
                InvalidTaskIdException.class,
                () -> commentService.createComment(user, 1, CreateCommentDto.builder()
                                .text("")
                                .build())
        );
    }

    @Test
    public void createComment_userNotPerformsTask_throwsInvalidTaskIdException() {
        User creator = createTestUser();
        User performer = createTestUser();
        Task task = createTestTask(creator, creator);

        userRepository.saveAndFlush(creator);
        userRepository.saveAndFlush(performer);
        taskRepository.saveAndFlush(task);

        Assertions.assertThrows(
                InvalidTaskIdException.class,
                () -> commentService.createComment(performer, task.getId(), CreateCommentDto.builder()
                        .text("")
                        .build())
        );
    }

    @Test
    @Transactional
    public void createComment_userPerformsTask_returnsComment() {
        User user = createTestUser();
        Task task = createTestTask(user, user);

        userRepository.saveAndFlush(user);
        taskRepository.saveAndFlush(task);

        CommentDto result = commentService.createComment(
                user,
                task.getId(),
                CreateCommentDto.builder()
                        .text("Comment")
                        .build()
        );

        Comment comment = commentRepository.findById(result.getId())
                .orElseThrow();

        Assertions.assertEquals(result, commentMapper.mapCommentToCommentDto(comment));
    }
}
