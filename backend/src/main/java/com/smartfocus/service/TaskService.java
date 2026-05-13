package com.smartfocus.service;

import com.smartfocus.domain.ActivityType;
import com.smartfocus.dto.TaskDtos;
import com.smartfocus.entity.Task;
import com.smartfocus.entity.User;
import com.smartfocus.exception.ApiException;
import com.smartfocus.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
  private final TaskRepository repo;
  private final ActivityLogService activityLogService;

  public TaskService(TaskRepository repo, ActivityLogService activityLogService) {
    this.repo = repo;
    this.activityLogService = activityLogService;
  }

  public List<TaskDtos.TaskResponse> listForUser(User user) {
    return repo.findAllByUserIdOrderByUpdatedAtDesc(user.getId()).stream().map(this::toDto).toList();
  }

  public TaskDtos.TaskResponse create(User user, TaskDtos.TaskRequest req) {
    Task t = new Task();
    t.setUser(user);
    apply(t, req);
    repo.save(t);
    activityLogService.log(user, ActivityType.TASK_CREATED, "Created task: " + t.getTitle());
    return toDto(t);
  }

  public TaskDtos.TaskResponse update(User user, Long id, TaskDtos.TaskRequest req) {
    Task t = repo.findByIdAndUserId(id, user.getId())
      .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Task not found"));
    apply(t, req);
    repo.save(t);
    activityLogService.log(user, ActivityType.TASK_UPDATED, "Updated task: " + t.getTitle());
    return toDto(t);
  }

  public void delete(User user, Long id) {
    Task t = repo.findByIdAndUserId(id, user.getId())
      .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Task not found"));
    repo.delete(t);
    activityLogService.log(user, ActivityType.TASK_DELETED, "Deleted task: " + t.getTitle());
  }

  public Task requireOwnedTask(User user, Long taskId) {
    return repo.findByIdAndUserId(taskId, user.getId())
      .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Task not found"));
  }

  private void apply(Task t, TaskDtos.TaskRequest req) {
    t.setTitle(req.title.trim());
    t.setDescription(req.description);
    t.setPriority(req.priority);
    t.setStatus(req.status);
    t.setEstimatedMinutes(req.estimatedMinutes);
  }

  private TaskDtos.TaskResponse toDto(Task t) {
    TaskDtos.TaskResponse dto = new TaskDtos.TaskResponse();
    dto.id = t.getId();
    dto.title = t.getTitle();
    dto.description = t.getDescription();
    dto.priority = t.getPriority();
    dto.status = t.getStatus();
    dto.estimatedMinutes = t.getEstimatedMinutes();
    dto.createdAt = t.getCreatedAt();
    dto.updatedAt = t.getUpdatedAt();
    return dto;
  }
}

