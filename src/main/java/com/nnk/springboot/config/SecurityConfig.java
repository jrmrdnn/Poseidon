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

  private static final String[] PUBLIC_PATHS = { "/", "/login", "/css/**" }; // Public paths
  private static final String LOGIN_PATH = "/login"; // Login page path
  private static final String LOGOUT_PATH = "/app-logout"; // Logout path
  private static final String SUCCESS_PATH = "/bidList/list"; // Redirect path after successful login

  /**
   * Configures the security filter chain for the application.
   * @param http the HttpSecurity object to configure
   * @return the configured SecurityFilterChain
   * @throws Exception if an error occurs during configuration
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
    throws Exception {
    http
      .authorizeHttpRequests(
        requests ->
          requests
            .requestMatchers(PUBLIC_PATHS) // Allow public access to specified paths
            .permitAll() // Permit all requests to public paths
            .anyRequest() // Any other request
            .authenticated() // Must be authenticated
      )
      .formLogin(
        form ->
          form
            .loginPage(LOGIN_PATH) // Custom login page
            .usernameParameter("username") // Username parameter name
            .passwordParameter("password") // Password parameter name
            .defaultSuccessUrl(SUCCESS_PATH, true) // Redirect after successful login
            .permitAll() // Permit all to access the login page
      )
      .logout(
        logout ->
          logout
            .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_PATH)) // Custom logout path
            .logoutSuccessUrl(LOGIN_PATH) // Redirect after successful logout
            .invalidateHttpSession(true) // Invalidate session on logout
            .deleteCookies("SESSION") // Delete session cookies
            .permitAll() // Permit all to access the logout page
      );

    return http.build(); // Build the security filter chain
  }

  /**
   * Configures the password encoder for the application.
   * @return the PasswordEncoder bean
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
