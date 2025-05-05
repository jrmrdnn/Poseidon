package com.nnk.springboot.controller;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * UserController handles requests related to User operations.
 */
@Controller
@RequestMapping("/user")
@Slf4j
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  /**
   * Shows the user list page.
   * @param redirectAttributes attributes for redirecting
   * @param model the model to be used in the view
   * @return User list page
   */
  @GetMapping("/list")
  public String showUser(RedirectAttributes redirectAttributes, Model model) {
    try {
      List<User> users = userService.getAllUsers();
      model.addAttribute("users", users);
    } catch (Exception e) {
      log.error("Error fetching users: " + e.getMessage());
      redirectAttributes.addFlashAttribute(
        "errorMessage",
        "Error fetching users"
      );
      model.addAttribute("users", List.of());
    }

    return "user/list";
  }

  /**
   * Shows the add user page.
   * @param model the model to be used in the view
   * @return User add page
   */
  @GetMapping("/add")
  public String showAddUserForm(Model model) {
    UserDto userDto = new UserDto();
    model.addAttribute("userDto", userDto);
    return "user/add";
  }

  /**
   * Shows the update user page.
   * @param id the id of the user to be updated
   * @param redirectAttributes attributes for redirecting
   * @param model the model to be used in the view
   * @return User update page
   */
  @GetMapping("/update/{id}")
  public String showUpdateForm(
    @PathVariable Integer id,
    RedirectAttributes redirectAttributes,
    Model model
  ) {
    try {
      UserDto userDto = userService.getUserById(id);
      model.addAttribute("userDto", userDto);
      model.addAttribute("userId", id);
    } catch (Exception e) {
      log.error("Error fetching user: " + e.getMessage());
      redirectAttributes.addFlashAttribute(
        "errorMessage",
        "Error fetching user"
      );
      return "redirect:/user/list";
    }

    return "user/update";
  }

  /**
   * Adds a new user.
   * @param userDto the user data transfer object
   * @param result the binding result
   * @param redirectAttributes attributes for redirecting
   * @param model the model to be used in the view
   * @return redirect to user list page
   */
  @PostMapping
  public String addUser(
    @Valid UserDto userDto,
    BindingResult result,
    RedirectAttributes redirectAttributes,
    Model model
  ) {
    if (result.hasErrors()) {
      model.addAttribute("userDto", userDto);
      return "user/add";
    }

    try {
      userService.addUser(userDto);
      redirectAttributes.addFlashAttribute(
        "successMessage",
        "User added successfully"
      );
    } catch (Exception e) {
      log.error("Error adding user: " + e.getMessage());
      redirectAttributes.addFlashAttribute("errorMessage", "Error adding user");
      return "redirect:/user/add";
    }

    return "redirect:/user/list";
  }

  /**
   * Updates an existing user.
   * @param id the id of the user to be updated
   * @param userDto the user data transfer object
   * @param result the binding result
   * @param redirectAttributes attributes for redirecting
   * @param model the model to be used in the view
   * @return redirect to user list page
   */
  @PutMapping("/{id}")
  public String updateUser(
    @PathVariable Integer id,
    @Valid UserDto userDto,
    BindingResult result,
    RedirectAttributes redirectAttributes,
    Model model
  ) {
    if (result.hasErrors()) {
      model.addAttribute("userDto", userDto);
      model.addAttribute("userId", id);
      return "user/update";
    }

    try {
      userService.updateUser(id, userDto);
      redirectAttributes.addFlashAttribute(
        "successMessage",
        "User updated successfully"
      );
    } catch (Exception e) {
      log.error("Error updating user: " + e.getMessage());
      redirectAttributes.addFlashAttribute(
        "errorMessage",
        "Error updating user"
      );
    }

    return "redirect:/user/list";
  }

  /**
   * Deletes an existing user.
   * @param id the id of the user to be deleted
   * @param redirectAttributes attributes for redirecting
   * @param model the model to be used in the view
   * @return redirect to user list page
   */
  @DeleteMapping("/{id}")
  public String deleteUser(
    @PathVariable Integer id,
    RedirectAttributes redirectAttributes,
    Model model
  ) {
    try {
      userService.deleteUser(id);
      redirectAttributes.addFlashAttribute(
        "successMessage",
        "User deleted successfully"
      );
    } catch (Exception e) {
      log.error("Error deleting user: " + e.getMessage());
      redirectAttributes.addFlashAttribute(
        "errorMessage",
        "Error deleting user"
      );
    }

    return "redirect:/user/list";
  }
}
