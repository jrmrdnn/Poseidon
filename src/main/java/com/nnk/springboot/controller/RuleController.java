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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@AllArgsConstructor
public class RuleController {

  private final RuleService ruleService;

  @GetMapping("/ruleName/list")
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

  @GetMapping("/ruleName/add")
  public String showAddRuleForm(Model model) {
    RuleDto ruleDto = new RuleDto();
    model.addAttribute("ruleDto", ruleDto);
    return "rule/add";
  }

  @GetMapping("/ruleName/update/{id}")
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

  @PostMapping("/ruleName/validate")
  public String validateRule(
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

  @PostMapping("/ruleName/update/{id}")
  public String updateRule(
    @PathVariable("id") Integer id,
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

  @PostMapping("/ruleName/delete/{id}")
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
