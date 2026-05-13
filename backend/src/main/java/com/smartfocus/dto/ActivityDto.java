package com.smartfocus.dto;

import com.smartfocus.domain.ActivityType;

import java.time.Instant;

public class ActivityDto {
  public Long id;
  public ActivityType type;
  public String message;
  public Instant createdAt;
}

