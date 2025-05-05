package com.nnk.springboot.controller;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDto;
import com.nnk.springboot.service.RatingService;
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
 * RatingController handles requests related to Rating operations.
 */
@Controller
@RequestMapping("/rating")
@Slf4j
@AllArgsConstructor
public class RatingController {

  private final RatingService ratingService;

  /**
   * Shows the rating list page.
   * @param redirectAttributes attributes for redirecting
   * @param model the model to be used in the view
   * @return Rating list page
   */
  @GetMapping("/list")
  public String showRating(RedirectAttributes redirectAttributes, Model model) {
    try {
      List<Rating> ratings = ratingService.getAllRatings();
      model.addAttribute("ratings", ratings);
    } catch (Exception e) {
      log.error("Error fetching ratings: " + e.getMessage());
      model.addAttribute("errorMessage", "Error fetching ratings");
      model.addAttribute("ratings", List.of());
    }

    return "rating/list";
  }

  /**
   * Shows the add rating page.
   * @param model the model to be used in the view
   * @return Rating add page
   */
  @GetMapping("/add")
  public String showAddRatingForm(Model model) {
    RatingDto ratingDto = new RatingDto();
    model.addAttribute("ratingDto", ratingDto);
    return "rating/add";
  }

  /**
   * Shows the update rating page.
   * @param id the ID of the rating to be updated
   * @param redirectAttributes attributes for redirecting
   * @param model the model to be used in the view
   * @return Rating update page
   */
  @GetMapping("/update/{id}")
  public String showUpdateRatingForm(
    @PathVariable Integer id,
    RedirectAttributes redirectAttributes,
    Model model
  ) {
    try {
      RatingDto ratingDto = ratingService.getRatingById(id);
      model.addAttribute("ratingDto", ratingDto);
      model.addAttribute("ratingId", id);
    } catch (Exception e) {
      log.error("Error fetching rating: " + e.getMessage());
      redirectAttributes.addFlashAttribute(
        "errorMessage",
        "Error fetching rating"
      );
      return "redirect:/rating/list";
    }

    return "rating/update";
  }

  /**
   * Adds a new rating.
   * @param ratingDto the rating data transfer object
   * @param result the binding result for validation errors
   * @param redirectAttributes attributes for redirecting
   * @param model the model to be used in the view
   * @return Redirects to the rating list page or shows the add page with errors
   */
  @PostMapping
  public String addRating(
    @Valid RatingDto ratingDto,
    BindingResult result,
    RedirectAttributes redirectAttributes,
    Model model
  ) {
    if (result.hasErrors()) {
      model.addAttribute("ratingDto", ratingDto);
      return "rating/add";
    }

    try {
      ratingService.addRating(ratingDto);
      redirectAttributes.addFlashAttribute(
        "successMessage",
        "Rating added successfully"
      );
    } catch (Exception e) {
      log.error("Error adding rating: " + e.getMessage());
      redirectAttributes.addFlashAttribute(
        "errorMessage",
        "Error adding rating"
      );
      return "redirect:/rating/add";
    }

    return "redirect:/rating/list";
  }

  /**
   * Updates an existing rating.
   * @param id the ID of the rating to be updated
   * @param ratingDto the rating data transfer object
   * @param result the binding result for validation errors
   * @param redirectAttributes attributes for redirecting
   * @param model the model to be used in the view
   * @return Redirects to the rating list page or shows the update page with errors
   */
  @PutMapping("/{id}")
  public String updateRating(
    @PathVariable Integer id,
    @Valid RatingDto ratingDto,
    BindingResult result,
    RedirectAttributes redirectAttributes,
    Model model
  ) {
    if (result.hasErrors()) {
      model.addAttribute("ratingDto", ratingDto);
      model.addAttribute("ratingId", id);
      return "rating/update";
    }

    try {
      ratingService.updateRating(id, ratingDto);
      redirectAttributes.addFlashAttribute(
        "successMessage",
        "Rating updated successfully"
      );
    } catch (Exception e) {
      log.error("Error updating rating: " + e.getMessage());
      redirectAttributes.addFlashAttribute(
        "errorMessage",
        "Error updating rating"
      );
    }

    return "redirect:/rating/list";
  }

  /**
   * Deletes a rating.
   * @param id the ID of the rating to be deleted
   * @param redirectAttributes attributes for redirecting
   * @param model the model to be used in the view
   * @return Redirects to the rating list page
   */
  @DeleteMapping("/{id}")
  public String deleteRating(
    @PathVariable Integer id,
    RedirectAttributes redirectAttributes,
    Model model
  ) {
    try {
      ratingService.deleteRating(id);
      redirectAttributes.addFlashAttribute(
        "successMessage",
        "Rating deleted successfully"
      );
    } catch (Exception e) {
      log.error("Error deleting rating: " + e.getMessage());
      redirectAttributes.addFlashAttribute(
        "errorMessage",
        "Error deleting rating"
      );
    }

    return "redirect:/rating/list";
  }
}
