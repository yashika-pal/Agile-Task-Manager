package com.smartfocus.controller;

import com.smartfocus.dto.TaskDtos;
import com.smartfocus.service.CurrentUserService;
import com.smartfocus.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
  private final CurrentUserService currentUserService;
  private final TaskService taskService;

  public TaskController(CurrentUserService currentUserService, TaskService taskService) {
    this.currentUserService = currentUserService;
    this.taskService = taskService;
  }

  @GetMapping
  public List<TaskDtos.TaskResponse> list() {
    var user = currentUserService.requireUser();
    return taskService.listForUser(user);
  }

  @PostMapping
  public TaskDtos.TaskResponse create(@Valid @RequestBody TaskDtos.TaskRequest req) {
    var user = currentUserService.requireUser();
    return taskService.create(user, req);
  }

  @PutMapping("/{id}")
  public TaskDtos.TaskResponse update(@PathVariable Long id, @Valid @RequestBody TaskDtos.TaskRequest req) {
    var user = currentUserService.requireUser();
    return taskService.update(user, id, req);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    var user = currentUserService.requireUser();
    taskService.delete(user, id);
  }
}

