package com.nnk.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * AuthController handles authentication-related requests.
 */
@Controller
public class AuthController {

  /**
   * Shows the login page.
   * @param redirectAttributes attributes for redirecting
   * @return Page
   */
  @GetMapping("/login")
  public String showLogin(RedirectAttributes redirectAttributes) {
    return "login";
  }
}
