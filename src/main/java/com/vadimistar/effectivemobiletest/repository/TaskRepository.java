package com.vadimistar.effectivemobiletest.repository;

import com.vadimistar.effectivemobiletest.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByIdAndPerformerId(long id, long performerId);
    List<Task> findByPerformerId(long performerId);
}
