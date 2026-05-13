package com.smartfocus.controller;

import com.smartfocus.dto.FocusDtos;
import com.smartfocus.service.CurrentUserService;
import com.smartfocus.service.FocusService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/focus")
public class FocusController {
  private final CurrentUserService currentUserService;
  private final FocusService focusService;

  public FocusController(CurrentUserService currentUserService, FocusService focusService) {
    this.currentUserService = currentUserService;
    this.focusService = focusService;
  }

  @PostMapping("/start")
  public FocusDtos.FocusSessionResponse start(@Valid @RequestBody FocusDtos.StartFocusRequest req) {
    var user = currentUserService.requireUser();
    return focusService.start(user, req);
  }

  @PostMapping("/complete")
  public FocusDtos.FocusSessionResponse complete(@Valid @RequestBody FocusDtos.CompleteFocusRequest req) {
    var user = currentUserService.requireUser();
    return focusService.complete(user, req);
  }
}

