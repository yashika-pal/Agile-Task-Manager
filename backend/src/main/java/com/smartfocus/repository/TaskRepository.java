package com.smartfocus.repository;

import com.smartfocus.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
  List<Task> findAllByUserIdOrderByUpdatedAtDesc(Long userId);
  Optional<Task> findByIdAndUserId(Long id, Long userId);
  long countByUserId(Long userId);
  long countByUserIdAndStatus(Long userId, com.smartfocus.domain.TaskStatus status);
}

