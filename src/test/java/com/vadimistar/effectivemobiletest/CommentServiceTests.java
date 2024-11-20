package com.vadimistar.effectivemobiletest;

import com.vadimistar.effectivemobiletest.dto.CommentDto;
import com.vadimistar.effectivemobiletest.dto.CreateCommentDto;
import com.vadimistar.effectivemobiletest.entity.Comment;
import com.vadimistar.effectivemobiletest.entity.Task;
import com.vadimistar.effectivemobiletest.entity.User;
import com.vadimistar.effectivemobiletest.entity.domain.Priority;
import com.vadimistar.effectivemobiletest.entity.domain.Status;
import com.vadimistar.effectivemobiletest.exception.InvalidTaskIdException;
import com.vadimistar.effectivemobiletest.mapper.CommentMapper;
import com.vadimistar.effectivemobiletest.repository.CommentRepository;
import com.vadimistar.effectivemobiletest.repository.TaskRepository;
import com.vadimistar.effectivemobiletest.repository.UserRepository;
import com.vadimistar.effectivemobiletest.service.CommentService;
import jakarta.transaction.Transactional;
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
import java.util.UUID;

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
    private static final MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("em-test-db");

    @DynamicPropertySource
    public static void mysqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
        registry.add("spring.datasource.driverClassName", mysqlContainer::getDriverClassName);
        registry.add("spring.flyway.enabled", () -> true);
    }

    @BeforeEach
    public void beforeEach() {
        commentRepository.deleteAll();
        taskRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void getComments_invalidTaskId_throwsInvalidTaskIdException() {
        User user = createUser();

        Assertions.assertThrows(
                InvalidTaskIdException.class,
                () -> commentService.getComments(user, 1, Pageable.unpaged())
        );
    }

    @Test
    public void getComments_userNotPerformsTask_throwsInvalidTaskIdException() {
        User user = createUser();
        Task task = createTask(user, null);

        Assertions.assertThrows(
                InvalidTaskIdException.class,
                () -> commentService.getComments(user, task.getId(), Pageable.unpaged())
        );
    }

    @Test
    @Transactional
    public void getComments_createComments_returnsAllComments() {
        User user = createUser();
        Task task = createTask(user, user);

        User user2 = createUser();

        List<Comment> comments = List.of(
                createComment(user, task),
                createComment(user2, task)
        );

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
        User user = createUser();

        Assertions.assertThrows(
                InvalidTaskIdException.class,
                () -> commentService.createComment(user, 1, CreateCommentDto.builder()
                                .text("")
                                .build())
        );
    }

    @Test
    public void createComment_userDoesNotPerformTask_throwsInvalidTaskIdException() {
        User creator = createUser();
        User performer = createUser();

        Task task = createTask(creator, creator);

        Assertions.assertThrows(
                InvalidTaskIdException.class,
                () -> commentService.createComment(performer, task.getId(), CreateCommentDto.builder()
                        .text("")
                        .build())
        );
    }

    @Test
    @Transactional
    public void createComment_validRequest_returnsCreateCommentDto() {
        User user = createUser();
        Task task = createTask(user, user);

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

    private User createUser() {
        User user = User.builder()
                .email("user" + UUID.randomUUID() + "@user")
                .password("1234")
                .build();

        userRepository.saveAndFlush(user);

        return user;
    }

    private Task createTask(User creator, User performer) {
        Task task = Task.builder()
                .title("Task title")
                .description("Task description")
                .status(Status.PENDING)
                .priority(Priority.HIGH)
                .creator(creator)
                .performer(performer)
                .build();

        taskRepository.saveAndFlush(task);

        return task;
    }

    private Comment createComment(User author, Task task) {
        Comment comment = Comment.builder()
                .text("Comment text")
                .author(author)
                .task(task)
                .build();

        commentRepository.saveAndFlush(comment);

        return comment;
    }
}
