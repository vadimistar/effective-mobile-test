package com.vadimistar.effectivemobiletest.repository;

import com.vadimistar.effectivemobiletest.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByTaskId(long taskId, Pageable pageable);
}
