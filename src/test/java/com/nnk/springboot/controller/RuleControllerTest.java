package com.nnk.springboot.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.dto.RuleDto;
import com.nnk.springboot.service.RuleService;
import java.util.Collections;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ExtendWith(MockitoExtension.class)
class RuleControllerTest {

  @Mock
  private RuleService ruleService;

  @Mock
  private Model model;

  @Mock
  private RedirectAttributes redirectAttributes;

  @Mock
  private BindingResult bindingResult;

  @InjectMocks
  private RuleController ruleController;

  @Nested
  public class TestShowRule {

    @Test
    public void showRule_shouldAddRulesToModel_andReturnListView() {
      Rule rule = new Rule();
      when(ruleService.getAllRules()).thenReturn(
        Collections.singletonList(rule)
      );

      String view = ruleController.showRule(redirectAttributes, model);
      verify(model).addAttribute("rules", Collections.singletonList(rule));
      assertThat(view).isEqualTo("rule/list");
    }

    @Test
    public void showRule_shouldHandleException_andReturnListView() {
      when(ruleService.getAllRules()).thenThrow(new RuntimeException());

      String view = ruleController.showRule(redirectAttributes, model);

      assertThat(view).isEqualTo("rule/list");
    }
  }

  @Nested
  public class TestShowAddRuleForm {

    @Test
    public void showAddRuleForm_shouldReturnAddView() {
      String view = ruleController.showAddRuleForm(model);

      verify(model).addAttribute(eq("ruleDto"), any(RuleDto.class));
      assertThat(view).isEqualTo("rule/add");
    }
  }

  @Nested
  public class TestShowUpdateRuleForm {

    @Test
    public void showUpdateRuleForm_shouldAddRuleToModel_andReturnUpdateView() {
      RuleDto ruleDto = new RuleDto();
      when(ruleService.getRuleById(1)).thenReturn(ruleDto);

      String view = ruleController.showUpdateRuleForm(
        1,
        redirectAttributes,
        model
      );

      verify(model).addAttribute("ruleDto", ruleDto);
      assertThat(view).isEqualTo("rule/update");
    }

    @Test
    public void showUpdateRuleForm_shouldHandleException_andRedirect() {
      when(ruleService.getRuleById(1)).thenThrow(new RuntimeException());

      String view = ruleController.showUpdateRuleForm(
        1,
        redirectAttributes,
        model
      );

      assertThat(view).isEqualTo("redirect:/ruleName/list");
    }
  }

  @Nested
  public class TestDeleteRule {

    @Test
    public void deleteRule_shouldDeleteRule_andRedirect() {
      String view = ruleController.deleteRule(1, redirectAttributes, model);

      verify(ruleService).deleteRule(1);
      assertThat(view).isEqualTo("redirect:/ruleName/list");
    }

    @Test
    public void deleteRule_shouldHandleException_andRedirect() {
      doThrow(new RuntimeException()).when(ruleService).deleteRule(1);

      String view = ruleController.deleteRule(1, redirectAttributes, model);

      assertThat(view).isEqualTo("redirect:/ruleName/list");
    }
  }

  @Nested
  public class TestValidateRule {

    @Test
    public void validateRule_shouldReturnAddView_whenBindingResultHasErrors() {
      RuleDto rule = new RuleDto();
      when(bindingResult.hasErrors()).thenReturn(true);

      String view = ruleController.validateRule(
        rule,
        bindingResult,
        redirectAttributes,
        model
      );

      assertThat(view).isEqualTo("rule/add");
      verifyNoInteractions(ruleService);
    }

    @Test
    public void validateRule_shouldAddRule_andRedirect_whenNoErrors() {
      RuleDto rule = new RuleDto();
      when(bindingResult.hasErrors()).thenReturn(false);

      String view = ruleController.validateRule(
        rule,
        bindingResult,
        redirectAttributes,
        model
      );

      verify(ruleService).addRule(any(RuleDto.class));
      assertThat(view).isEqualTo("redirect:/ruleName/list");
    }

    @Test
    public void validateRule_shouldHandleException_andRedirect() {
      RuleDto rule = new RuleDto();
      when(bindingResult.hasErrors()).thenReturn(false);
      doThrow(new RuntimeException())
        .when(ruleService)
        .addRule(any(RuleDto.class));

      String view = ruleController.validateRule(
        rule,
        bindingResult,
        redirectAttributes,
        model
      );

      verify(ruleService).addRule(any(RuleDto.class));
      assertThat(view).isEqualTo("redirect:/ruleName/add");
    }
  }

  @Nested
  public class TestUpdateRule {

    @Test
    public void updateRule_shouldReturnUpdateView_whenBindingResultHasErrors() {
      RuleDto rule = new RuleDto();
      when(bindingResult.hasErrors()).thenReturn(true);

      String view = ruleController.updateRule(
        1,
        rule,
        bindingResult,
        redirectAttributes,
        model
      );

      assertThat(view).isEqualTo("rule/update");
      verifyNoInteractions(ruleService);
    }

    @Test
    public void updateRule_shouldUpdateRule_andRedirect_whenNoErrors() {
      RuleDto rule = new RuleDto();
      when(bindingResult.hasErrors()).thenReturn(false);

      String view = ruleController.updateRule(
        1,
        rule,
        bindingResult,
        redirectAttributes,
        model
      );

      verify(ruleService).updateRule(eq(1), any(RuleDto.class));
      assertThat(view).isEqualTo("redirect:/ruleName/list");
    }

    @Test
    public void updateRule_shouldHandleException_andRedirect() {
      RuleDto rule = new RuleDto();
      when(bindingResult.hasErrors()).thenReturn(false);
      doThrow(new RuntimeException())
        .when(ruleService)
        .updateRule(eq(1), any(RuleDto.class));

      String view = ruleController.updateRule(
        1,
        rule,
        bindingResult,
        redirectAttributes,
        model
      );

      verify(ruleService).updateRule(eq(1), any(RuleDto.class));
      assertThat(view).isEqualTo("redirect:/ruleName/list");
    }
  }
}
