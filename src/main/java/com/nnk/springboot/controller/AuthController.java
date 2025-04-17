package com.nnk.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

  @GetMapping("/login")
  public String showLogin(RedirectAttributes redirectAttributes) {
    return "login";
  }
}
