package com.smartfocus.dto;

import com.smartfocus.domain.Priority;
import com.smartfocus.domain.TaskStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public class TaskDtos {

  public static class TaskRequest {
    @NotBlank
    @Size(max = 120)
    public String title;

    public String description;

    @NotNull
    public Priority priority;

    @NotNull
    public TaskStatus status;

    @NotNull
    @Min(1)
    @Max(24 * 60)
    public Integer estimatedMinutes;
  }

  public static class TaskResponse {
    public Long id;
    public String title;
    public String description;
    public Priority priority;
    public TaskStatus status;
    public Integer estimatedMinutes;
    public Instant createdAt;
    public Instant updatedAt;
  }
}

