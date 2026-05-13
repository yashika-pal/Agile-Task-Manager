package com.smartfocus.service;

import com.smartfocus.dto.AuthDtos;
import com.smartfocus.entity.User;
import com.smartfocus.exception.ApiException;
import com.smartfocus.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
  }

  public AuthDtos.AuthResponse register(AuthDtos.RegisterRequest req) {
    if (userRepository.existsByUsernameIgnoreCase(req.username)) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "Username already taken");
    }

    User u = new User();
    u.setUsername(req.username.trim());
    u.setPasswordHash(passwordEncoder.encode(req.password));
    userRepository.save(u);

    AuthDtos.AuthResponse res = new AuthDtos.AuthResponse();
    res.id = u.getId();
    res.username = u.getUsername();
    return res;
  }

  public AuthDtos.AuthResponse login(AuthDtos.LoginRequest req, HttpServletRequest httpRequest) {
    Authentication auth = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(req.username, req.password)
    );
    SecurityContextHolder.getContext().setAuthentication(auth);

    // Persist SecurityContext into session so future requests are authenticated via cookie.
    HttpSession session = httpRequest.getSession(true);
    session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

    User u = userRepository.findByUsernameIgnoreCase(req.username)
      .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "Invalid username/password"));

    AuthDtos.AuthResponse res = new AuthDtos.AuthResponse();
    res.id = u.getId();
    res.username = u.getUsername();
    return res;
  }

  public void logout(HttpServletRequest req) {
    SecurityContextHolder.clearContext();
    HttpSession session = req.getSession(false);
    if (session != null) session.invalidate();
  }
}

