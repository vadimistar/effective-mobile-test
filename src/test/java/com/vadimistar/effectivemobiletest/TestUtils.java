package com.vadimistar.effectivemobiletest;

import com.vadimistar.effectivemobiletest.entity.Comment;
import com.vadimistar.effectivemobiletest.entity.Task;
import com.vadimistar.effectivemobiletest.entity.User;
import com.vadimistar.effectivemobiletest.entity.domain.Priority;
import com.vadimistar.effectivemobiletest.entity.domain.Status;
import lombok.experimental.UtilityClass;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.MySQLContainer;

import java.util.UUID;

@UtilityClass
public class TestUtils {

    public static MySQLContainer<?> createMySQLContainer() {
        return new MySQLContainer<>("mysql:8.0");
    }

    public static void fillMySQLProperties(DynamicPropertyRegistry registry, MySQLContainer<?> mysqlContainer) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
        registry.add("spring.datasource.driverClassName", mysqlContainer::getDriverClassName);
        registry.add("spring.flyway.enabled", () -> true);
    }

    public static User createTestUser() {
        return User.builder()
                .email("user" + UUID.randomUUID() + "@user")
                .password("1234")
                .build();
    }

    public static Task createTestTask(User creator, User performer) {
        return Task.builder()
                .title("Task title")
                .description("Task description")
                .status(Status.PENDING)
                .priority(Priority.HIGH)
                .creator(creator)
                .performer(performer)
                .build();
    }

    public static Comment createTestComment(User author, Task task) {
        return Comment.builder()
                .text("Comment text")
                .author(author)
                .task(task)
                .build();
    }
}
