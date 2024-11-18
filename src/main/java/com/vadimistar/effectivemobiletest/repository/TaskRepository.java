package com.vadimistar.effectivemobiletest.repository;

import com.vadimistar.effectivemobiletest.entity.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByIdAndPerformerId(long id, long performerId);
    List<Task> findByPerformerId(long performerId, Pageable pageable);

    @Query("select t from Task t where (:creator_id is null or t.creator.id = :creator_id) " +
            "and (:performer_id is null or t.performer.id = :performer_id)")
    List<Task> findByCreatorIdAndPerformerId(
            @Param("creator_id") Long creatorId,
            @Param("performer_id") Long performerId,
            Pageable pageable
    );
}
