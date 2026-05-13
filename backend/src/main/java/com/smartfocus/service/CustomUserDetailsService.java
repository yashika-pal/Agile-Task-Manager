package com.smartfocus.service;

import com.smartfocus.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var u = userRepository.findByUsernameIgnoreCase(username)
      .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    // Simple app: a single USER role for everyone.
    return User.withUsername(u.getUsername())
      .password(u.getPasswordHash())
      .roles("USER")
      .build();
  }
}

