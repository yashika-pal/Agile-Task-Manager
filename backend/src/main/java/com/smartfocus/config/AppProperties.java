package com.smartfocus.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {
  private final Cors cors = new Cors();

  public Cors getCors() {
    return cors;
  }

  public static class Cors {
    private String allowedOrigins = "http://localhost:4200";

    public String getAllowedOrigins() {
      return allowedOrigins;
    }

    public void setAllowedOrigins(String allowedOrigins) {
      this.allowedOrigins = allowedOrigins;
    }
  }
}

