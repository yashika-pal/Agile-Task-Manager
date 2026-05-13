package com.smartfocus.controller;

import com.smartfocus.dto.DashboardDto;
import com.smartfocus.service.CurrentUserService;
import com.smartfocus.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
  private final CurrentUserService currentUserService;
  private final DashboardService dashboardService;

  public DashboardController(CurrentUserService currentUserService, DashboardService dashboardService) {
    this.currentUserService = currentUserService;
    this.dashboardService = dashboardService;
  }

  @GetMapping
  public DashboardDto get() {
    var user = currentUserService.requireUser();
    return dashboardService.forUser(user);
  }
}

