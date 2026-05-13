package com.smartfocus.service;

import com.smartfocus.domain.ActivityType;
import com.smartfocus.dto.FocusDtos;
import com.smartfocus.entity.FocusSession;
import com.smartfocus.entity.Task;
import com.smartfocus.entity.User;
import com.smartfocus.exception.ApiException;
import com.smartfocus.repository.FocusSessionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class FocusService {
  private final FocusSessionRepository repo;
  private final TaskService taskService;
  private final ActivityLogService activityLogService;

  public FocusService(FocusSessionRepository repo, TaskService taskService, ActivityLogService activityLogService) {
    this.repo = repo;
    this.taskService = taskService;
    this.activityLogService = activityLogService;
  }

  public FocusDtos.FocusSessionResponse start(User user, FocusDtos.StartFocusRequest req) {
    Task task = taskService.requireOwnedTask(user, req.taskId);

    FocusSession fs = new FocusSession();
    fs.setTask(task);
    fs.setPlannedMinutes(req.plannedMinutes);
    repo.save(fs);

    activityLogService.log(user, ActivityType.FOCUS_SESSION_STARTED, "Started focus session for: " + task.getTitle());
    return toDto(fs);
  }

  public FocusDtos.FocusSessionResponse complete(User user, FocusDtos.CompleteFocusRequest req) {
    FocusSession fs = repo.findByIdForUser(req.focusSessionId, user.getId())
      .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Focus session not found"));

    if (fs.isCompleted()) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "Focus session already completed");
    }

    fs.setActualMinutes(req.actualMinutes);
    fs.setCompleted(true);
    fs.setCompletedAt(Instant.now());
    repo.save(fs);

    activityLogService.log(user, ActivityType.FOCUS_SESSION_COMPLETED,
      "Completed focus session (" + req.actualMinutes + " min) for: " + fs.getTask().getTitle());
    return toDto(fs);
  }

  private FocusDtos.FocusSessionResponse toDto(FocusSession fs) {
    FocusDtos.FocusSessionResponse dto = new FocusDtos.FocusSessionResponse();
    dto.id = fs.getId();
    dto.taskId = fs.getTask().getId();
    dto.plannedMinutes = fs.getPlannedMinutes();
    dto.actualMinutes = fs.getActualMinutes();
    dto.completed = fs.isCompleted();
    dto.startedAt = fs.getStartedAt();
    dto.completedAt = fs.getCompletedAt();
    return dto;
  }
}

