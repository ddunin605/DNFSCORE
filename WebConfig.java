package com.dunin.dnfscore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class WebConfig {

  // 필요 시 GitHub Pages 도메인만 허용 (예: https://ddunin605.github.io)
  private static final String FRONT_ORIGIN = "*"; // 배포 시 특정 도메인으로 바꾸는 걸 권장

  @Bean
  public CorsFilter corsFilter() {
    CorsConfiguration cfg = new CorsConfiguration();
    cfg.addAllowedOrigin(FRONT_ORIGIN);
    cfg.addAllowedHeader("*");
    cfg.addAllowedMethod("*");
    UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
    src.registerCorsConfiguration("/**", cfg);
    return new CorsFilter(src);
  }
}
