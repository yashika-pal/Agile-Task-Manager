package com.smartfocus.service;

import com.smartfocus.domain.ActivityType;
import com.smartfocus.dto.ActivityDto;
import com.smartfocus.entity.ActivityLog;
import com.smartfocus.entity.User;
import com.smartfocus.repository.ActivityLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityLogService {
  private final ActivityLogRepository repo;

  public ActivityLogService(ActivityLogRepository repo) {
    this.repo = repo;
  }

  public void log(User user, ActivityType type, String message) {
    ActivityLog al = new ActivityLog();
    al.setUser(user);
    al.setType(type);
    al.setMessage(message);
    repo.save(al);
  }

  public List<ActivityDto> latestForUser(Long userId) {
    return repo.findTop50ByUserIdOrderByCreatedAtDesc(userId).stream().map(this::toDto).toList();
  }

  private ActivityDto toDto(ActivityLog al) {
    ActivityDto dto = new ActivityDto();
    dto.id = al.getId();
    dto.type = al.getType();
    dto.message = al.getMessage();
    dto.createdAt = al.getCreatedAt();
    return dto;
  }
}

