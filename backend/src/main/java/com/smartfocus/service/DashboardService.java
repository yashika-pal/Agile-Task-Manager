package com.smartfocus.service;

import com.smartfocus.domain.TaskStatus;
import com.smartfocus.dto.DashboardDto;
import com.smartfocus.entity.User;
import com.smartfocus.repository.FocusSessionRepository;
import com.smartfocus.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
  private final TaskRepository taskRepository;
  private final FocusSessionRepository focusSessionRepository;

  public DashboardService(TaskRepository taskRepository, FocusSessionRepository focusSessionRepository) {
    this.taskRepository = taskRepository;
    this.focusSessionRepository = focusSessionRepository;
  }

  public DashboardDto forUser(User user) {
    DashboardDto dto = new DashboardDto();
    dto.totalTasks = taskRepository.countByUserId(user.getId());
    dto.completedTasks = taskRepository.countByUserIdAndStatus(user.getId(), TaskStatus.DONE);
    dto.totalFocusMinutes = focusSessionRepository.sumCompletedMinutesForUser(user.getId());
    return dto;
  }
}

