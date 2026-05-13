package com.smartfocus.controller;

import com.smartfocus.dto.AuthDtos;
import com.smartfocus.service.CurrentUserService;
import com.smartfocus.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final AuthService authService;
  private final CurrentUserService currentUserService;

  public AuthController(AuthService authService, CurrentUserService currentUserService) {
    this.authService = authService;
    this.currentUserService = currentUserService;
  }

  @PostMapping("/register")
  public AuthDtos.AuthResponse register(@Valid @RequestBody AuthDtos.RegisterRequest req) {
    return authService.register(req);
  }

  @PostMapping("/login")
  public AuthDtos.AuthResponse login(@Valid @RequestBody AuthDtos.LoginRequest req, HttpServletRequest httpRequest) {
    return authService.login(req, httpRequest);
  }

  @PostMapping("/logout")
  public void logout(HttpServletRequest req) {
    authService.logout(req);
  }

  @GetMapping("/me")
  public AuthDtos.AuthResponse me() {
    var user = currentUserService.requireUser();
    AuthDtos.AuthResponse res = new AuthDtos.AuthResponse();
    res.id = user.getId();
    res.username = user.getUsername();
    return res;
  }
}

