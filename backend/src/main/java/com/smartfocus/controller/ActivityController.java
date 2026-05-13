package com.smartfocus.controller;

import com.smartfocus.dto.ActivityDto;
import com.smartfocus.service.ActivityLogService;
import com.smartfocus.service.CurrentUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {
  private final CurrentUserService currentUserService;
  private final ActivityLogService activityLogService;

  public ActivityController(CurrentUserService currentUserService, ActivityLogService activityLogService) {
    this.currentUserService = currentUserService;
    this.activityLogService = activityLogService;
  }

  @GetMapping
  public List<ActivityDto> latest() {
    var user = currentUserService.requireUser();
    return activityLogService.latestForUser(user.getId());
  }
}

