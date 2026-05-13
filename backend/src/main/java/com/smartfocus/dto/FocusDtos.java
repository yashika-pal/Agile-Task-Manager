package com.smartfocus.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public class FocusDtos {

  public static class StartFocusRequest {
    @NotNull
    public Long taskId;

    @NotNull
    @Min(1)
    @Max(180)
    public Integer plannedMinutes;
  }

  public static class CompleteFocusRequest {
    @NotNull
    public Long focusSessionId;

    @NotNull
    @Min(1)
    @Max(240)
    public Integer actualMinutes;
  }

  public static class FocusSessionResponse {
    public Long id;
    public Long taskId;
    public Integer plannedMinutes;
    public Integer actualMinutes;
    public boolean completed;
    public Instant startedAt;
    public Instant completedAt;
  }
}

