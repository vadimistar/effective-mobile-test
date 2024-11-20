package com.vadimistar.effectivemobiletest;

import com.vadimistar.effectivemobiletest.dto.TaskDto;
import com.vadimistar.effectivemobiletest.dto.UpdateTaskDto;
import com.vadimistar.effectivemobiletest.entity.Task;
import com.vadimistar.effectivemobiletest.entity.User;
import com.vadimistar.effectivemobiletest.entity.domain.Priority;
import com.vadimistar.effectivemobiletest.entity.domain.Status;
import com.vadimistar.effectivemobiletest.exception.TaskNotFoundException;
import com.vadimistar.effectivemobiletest.mapper.TaskMapper;
import com.vadimistar.effectivemobiletest.repository.TaskRepository;
import com.vadimistar.effectivemobiletest.repository.UserRepository;
import com.vadimistar.effectivemobiletest.service.TaskService;
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

@SpringBootTest
@Testcontainers
public class TaskServiceTests {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

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
        taskRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void getTask_invalidTaskId_throwsTaskNotFoundException() {
        User user = createUser();

        Assertions.assertThrows(TaskNotFoundException.class, () -> taskService.getTask(user, 1));
    }

    @Test
    public void getTask_userNotPerformsTask_throwsTaskNotFoundException() {
        User user = createUser();
        Task task = createTask(user, null);

        Assertions.assertThrows(TaskNotFoundException.class, () -> taskService.getTask(user, task.getId()));
    }

    @Test
    public void getTask_userPerformsTask_returnsTask() {
        User creator = createUser("creator@email");
        User performer = createUser("performer@email");

        Task task = createTask(creator, performer);

        TaskDto expected = taskMapper.mapTaskToTaskDto(task);

        Assertions.assertEquals(expected, taskService.getTask(performer, task.getId()));
    }

    @Test
    public void getTasks_createSomeTasks_returnsOnlyPerformedTasks() {
        User creator = createUser("creator@email");
        User performer = createUser("performer@email");

        createTask(creator, creator);
        createTask(creator, creator);

        List<Task> performedTasks = List.of(
                createTask(creator, performer),
                createTask(creator, performer)
        );

        List<TaskDto> expected = performedTasks.stream()
                .map(taskMapper::mapTaskToTaskDto)
                .toList();

        Assertions.assertIterableEquals(expected, taskService.getTasks(performer, Pageable.unpaged()));
    }

    @Test
    public void updateTask_invalidTaskId_throwsTaskNotFoundException() {
        User user = createUser();

        Assertions.assertThrows(
                TaskNotFoundException.class,
                () -> taskService.updateTask(user, 1, UpdateTaskDto.builder()
                        .status(Status.PENDING)
                        .build())
        );
    }

    @Test
    public void updateTask_newStatus_returnsTaskWithNewStatus() {
        User user = createUser();

        Task task = createTask(user, user);

        TaskDto expected = taskMapper.mapTaskToTaskDto(task);
        expected.setStatus(Status.DONE);

        Assertions.assertEquals(
                expected,
                taskService.updateTask(user, task.getId(), UpdateTaskDto.builder()
                        .status(Status.DONE)
                        .build())
        );
    }

    private User createUser() {
        return createUser("user@user");
    }

    private User createUser(String email) {
        User user = User.builder()
                .email(email)
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
}
