package com.smartfocus.service;

import com.smartfocus.entity.User;
import com.smartfocus.exception.ApiException;
import com.smartfocus.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
  private final UserRepository userRepository;

  public CurrentUserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User requireUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || auth.getName() == null) {
      throw new ApiException(HttpStatus.UNAUTHORIZED, "Not logged in");
    }
    return userRepository.findByUsernameIgnoreCase(auth.getName())
      .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "User not found"));
  }
}

