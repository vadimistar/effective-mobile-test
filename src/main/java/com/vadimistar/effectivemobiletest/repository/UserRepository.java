package com.vadimistar.effectivemobiletest.repository;

import com.vadimistar.effectivemobiletest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
}
