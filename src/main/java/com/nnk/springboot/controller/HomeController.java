package com.nnk.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * HomeController handles requests related to the home page and admin home page.
 */
@Controller
public class HomeController {

  /**
   * Shows the home page.
   * @param model the model to be used in the view
   * @return home page
   */
  @GetMapping("/")
  public String home(Model model) {
    return "home";
  }

  /**
   * Redirects to the admin home page.
   * @param model the model to be used in the view
   * @return redirect to admin home page
   */
  @GetMapping("/admin/home")
  public String adminHome(Model model) {
    return "redirect:/bidList/list";
  }
}
