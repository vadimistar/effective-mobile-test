package com.vadimistar.effectivemobiletest;

import com.vadimistar.effectivemobiletest.dto.TaskDto;
import com.vadimistar.effectivemobiletest.dto.UpdateTaskDto;
import com.vadimistar.effectivemobiletest.entity.Task;
import com.vadimistar.effectivemobiletest.entity.User;
import com.vadimistar.effectivemobiletest.entity.domain.Status;
import com.vadimistar.effectivemobiletest.exception.TaskNotFoundException;
import com.vadimistar.effectivemobiletest.mapper.TaskMapper;
import com.vadimistar.effectivemobiletest.repository.TaskRepository;
import com.vadimistar.effectivemobiletest.repository.UserRepository;
import com.vadimistar.effectivemobiletest.service.TaskService;
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
        taskRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void getTask_invalidTaskId_throwsTaskNotFoundException() {
        User user = createTestUser();

        userRepository.saveAndFlush(user);

        Assertions.assertThrows(TaskNotFoundException.class, () -> taskService.getTask(user, 1));
    }

    @Test
    public void getTask_userNotPerformsTask_throwsTaskNotFoundException() {
        User user = createTestUser();
        Task task = createTestTask(user, null);

        userRepository.saveAndFlush(user);
        taskRepository.saveAndFlush(task);

        Assertions.assertThrows(TaskNotFoundException.class, () -> taskService.getTask(user, task.getId()));
    }

    @Test
    public void getTask_userPerformsTask_returnsTask() {
        User creator = createTestUser();
        User performer = createTestUser();
        Task task = createTestTask(creator, performer);

        userRepository.saveAndFlush(creator);
        userRepository.saveAndFlush(performer);
        taskRepository.saveAndFlush(task);

        TaskDto expected = taskMapper.mapTaskToTaskDto(task);

        Assertions.assertEquals(expected, taskService.getTask(performer, task.getId()));
    }

    @Test
    public void getTasks_createTasks_returnsOnlyPerformedTasks() {
        User creator = createTestUser();
        User performer = createTestUser();

        List<Task> notPerformedTasks = List.of(
                createTestTask(creator, creator),
                createTestTask(creator, creator)
        );
        List<Task> performedTasks = List.of(
                createTestTask(creator, performer),
                createTestTask(creator, performer)
        );

        userRepository.saveAndFlush(creator);
        userRepository.saveAndFlush(performer);

        taskRepository.saveAllAndFlush(notPerformedTasks);
        taskRepository.saveAllAndFlush(performedTasks);

        List<TaskDto> expected = performedTasks.stream()
                .map(taskMapper::mapTaskToTaskDto)
                .toList();

        Assertions.assertIterableEquals(expected, taskService.getTasks(performer, Pageable.unpaged()));
    }

    @Test
    public void updateTask_invalidTaskId_throwsTaskNotFoundException() {
        User user = createTestUser();

        userRepository.saveAndFlush(user);

        Assertions.assertThrows(
                TaskNotFoundException.class,
                () -> taskService.updateTask(user, 1, UpdateTaskDto.builder()
                        .status(Status.PENDING)
                        .build())
        );
    }

    @Test
    public void updateTask_newStatus_returnsTaskWithNewStatus() {
        User user = createTestUser();
        Task task = createTestTask(user, user);

        userRepository.saveAndFlush(user);
        taskRepository.saveAndFlush(task);

        TaskDto expected = taskMapper.mapTaskToTaskDto(task);
        expected.setStatus(Status.DONE);

        Assertions.assertEquals(
                expected,
                taskService.updateTask(user, task.getId(), UpdateTaskDto.builder()
                        .status(Status.DONE)
                        .build())
        );
    }
}
