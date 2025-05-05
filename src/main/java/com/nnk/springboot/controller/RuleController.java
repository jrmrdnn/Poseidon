package com.nnk.springboot.controller;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.dto.RuleDto;
import com.nnk.springboot.service.RuleService;
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
 * RuleController handles requests related to Rule operations.
 */
@Controller
@RequestMapping("/ruleName")
@Slf4j
@AllArgsConstructor
public class RuleController {

  private final RuleService ruleService;

  /**
   * Shows the rule list page.
   * @param redirectAttributes attributes for redirecting
   * @param model the model to be used in the view
   * @return Rule list page
   */
  @GetMapping("/list")
  public String showRule(RedirectAttributes redirectAttributes, Model model) {
    try {
      List<Rule> rules = ruleService.getAllRules();
      model.addAttribute("rules", rules);
    } catch (Exception e) {
      log.error("Error fetching rules: " + e.getMessage());
      model.addAttribute("errorMessage", "Error fetching rules");
      model.addAttribute("rules", List.of());
    }

    return "rule/list";
  }

  /**
   * Shows the add rule page.
   * @param model the model to be used in the view
   * @return Rule add page
   */
  @GetMapping("/add")
  public String showAddRuleForm(Model model) {
    RuleDto ruleDto = new RuleDto();
    model.addAttribute("ruleDto", ruleDto);
    return "rule/add";
  }

  /**
   * Shows the update rule page.
   * @param id the ID of the rule to be updated
   * @param redirectAttributes attributes for redirecting
   * @param model the model to be used in the view
   * @return Rule update page
   */
  @GetMapping("/update/{id}")
  public String showUpdateRuleForm(
    @PathVariable Integer id,
    RedirectAttributes redirectAttributes,
    Model model
  ) {
    try {
      RuleDto ruleDto = ruleService.getRuleById(id);
      model.addAttribute("ruleDto", ruleDto);
      model.addAttribute("ruleId", id);
    } catch (Exception e) {
      log.error("Error fetching rule: " + e.getMessage());
      redirectAttributes.addFlashAttribute(
        "errorMessage",
        "Error fetching rule"
      );
      return "redirect:/ruleName/list";
    }

    return "rule/update";
  }

  /**
   * Shows the delete rule page.
   * @param id the ID of the rule to be deleted
   * @param redirectAttributes attributes for redirecting
   * @param model the model to be used in the view
   * @return Rule delete page
   */
  @PostMapping
  public String addRule(
    @Valid RuleDto ruleDto,
    BindingResult result,
    RedirectAttributes redirectAttributes,
    Model model
  ) {
    if (result.hasErrors()) {
      model.addAttribute("ruleDto", ruleDto);
      return "rule/add";
    }

    try {
      ruleService.addRule(ruleDto);
      redirectAttributes.addFlashAttribute(
        "successMessage",
        "Rule added successfully"
      );
    } catch (Exception e) {
      log.error("Error adding rule: " + e.getMessage());
      redirectAttributes.addFlashAttribute("errorMessage", "Error adding rule");
      return "redirect:/ruleName/add";
    }

    return "redirect:/ruleName/list";
  }

  /**
   * Updates an existing rule.
   * @param id the ID of the rule to be updated
   * @param ruleDto the updated rule details
   * @param result binding result for validation errors
   * @param redirectAttributes attributes for redirecting
   * @param model the model to be used in the view
   * @return Redirect to the rule list page
   */
  @PutMapping("/{id}")
  public String updateRule(
    @PathVariable Integer id,
    @Valid RuleDto ruleDto,
    BindingResult result,
    RedirectAttributes redirectAttributes,
    Model model
  ) {
    if (result.hasErrors()) {
      model.addAttribute("ruleDto", ruleDto);
      model.addAttribute("ruleId", id);
      return "rule/update";
    }

    try {
      ruleService.updateRule(id, ruleDto);
      redirectAttributes.addFlashAttribute(
        "successMessage",
        "Rule updated successfully"
      );
    } catch (Exception e) {
      log.error("Error updating rule: " + e.getMessage());
      redirectAttributes.addFlashAttribute(
        "errorMessage",
        "Error updating rule"
      );
    }

    return "redirect:/ruleName/list";
  }

  /**
   * Deletes a rule.
   * @param id the ID of the rule to be deleted
   * @param redirectAttributes attributes for redirecting
   * @return Redirect to the rule list page
   */
  @DeleteMapping("/{id}")
  public String deleteRule(
    @PathVariable Integer id,
    RedirectAttributes redirectAttributes,
    Model model
  ) {
    try {
      ruleService.deleteRule(id);
      redirectAttributes.addFlashAttribute(
        "successMessage",
        "Rule deleted successfully"
      );
    } catch (Exception e) {
      log.error("Error deleting rule: " + e.getMessage());
      redirectAttributes.addFlashAttribute(
        "errorMessage",
        "Error deleting rule"
      );
    }

    return "redirect:/ruleName/list";
  }
}
