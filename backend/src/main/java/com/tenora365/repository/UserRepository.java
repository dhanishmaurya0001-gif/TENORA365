package com.tenora365.repository;

import com.tenora365.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = "school")
    Optional<User> findByUsername(String username);

    List<User> findBySchoolId(Long schoolId);
}