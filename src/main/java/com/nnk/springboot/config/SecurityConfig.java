package com.nnk.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private static final String[] PUBLIC_PATHS = { "/**", "/login", "/css/**" };
  private static final String LOGIN_PATH = "/login";
  private static final String LOGOUT_PATH = "/app-logout";
  private static final String SUCCESS_PATH = "/bidList/list";

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
    throws Exception {
    http
      .authorizeHttpRequests(requests ->
        requests
          .requestMatchers(PUBLIC_PATHS)
          .permitAll()
          .anyRequest()
          .authenticated()
      )
      .formLogin(form ->
        form
          .loginPage(LOGIN_PATH)
          .usernameParameter("username")
          .passwordParameter("password")
          .defaultSuccessUrl(SUCCESS_PATH, true)
          .permitAll()
      )
      .logout(logout ->
        logout
          .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_PATH))
          .logoutSuccessUrl(LOGIN_PATH)
          .invalidateHttpSession(true)
          .deleteCookies("SESSION")
          .permitAll()
      );

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
