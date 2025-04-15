package com.nnk.springboot.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.dto.RuleDto;
import com.nnk.springboot.repository.RuleRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RuleServiceTest {

  @Mock
  private RuleRepository ruleRepository;

  @Mock
  private RuleMapper ruleMapper;

  @InjectMocks
  private RuleService ruleService;

  @Nested
  public class TestGetAllRule {

    @Test
    public void getAllRules_shouldReturnAllRules() {
      List<Rule> rules = Arrays.asList(new Rule(), new Rule());
      when(ruleRepository.findAll()).thenReturn(rules);

      List<Rule> result = ruleService.getAllRules();

      assertThat(result).hasSize(2);
      verify(ruleRepository).findAll();
    }

    @Test
    public void getAllRules_shouldReturnEmptyList_whenNoRules() {
      when(ruleRepository.findAll()).thenReturn(Collections.emptyList());

      List<Rule> result = ruleService.getAllRules();

      assertThat(result).isEmpty();
      verify(ruleRepository).findAll();
    }
  }

  @Nested
  public class TestGetRuleById {

    @Test
    public void getRuleById_shouldReturnRule_whenIdExists() {
      Rule rule = new Rule();
      RuleDto ruleDto = new RuleDto();

      when(ruleRepository.findById(1)).thenReturn(Optional.of(rule));
      when(ruleMapper.ruleToRuleDto(rule)).thenReturn(ruleDto);

      RuleDto result = ruleService.getRuleById(1);

      assertThat(result).isEqualTo(ruleDto);
      verify(ruleRepository).findById(1);
      verify(ruleMapper).ruleToRuleDto(rule);
    }

    @Test
    public void getRuleById_shouldThrowException_whenIdDoesNotExist() {
      when(ruleRepository.findById(99)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> ruleService.getRuleById(99))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Rule not found");
    }
  }

  @Nested
  public class TestAddRule {

    @Test
    public void addRule_shouldSaveNewRule() {
      RuleDto ruleDto = new RuleDto();
      Rule rule = new Rule();

      when(ruleMapper.ruleDtoToRule(ruleDto)).thenReturn(rule);

      ruleService.addRule(ruleDto);

      verify(ruleMapper).ruleDtoToRule(ruleDto);
      verify(ruleRepository).save(rule);
    }
  }

  @Nested
  public class TestUpdateRule {

    @Test
    public void updateRule_shouldUpdateExistingRule() {
      Rule existingRule = new Rule();
      RuleDto updatedRuleDto = new RuleDto();

      when(ruleRepository.findById(1)).thenReturn(Optional.of(existingRule));
      when(ruleMapper.ruleDtoToRule(1, updatedRuleDto)).thenReturn(
        existingRule
      );

      ruleService.updateRule(1, updatedRuleDto);

      verify(ruleRepository).findById(1);
      verify(ruleMapper).ruleDtoToRule(1, updatedRuleDto);
      verify(ruleRepository).save(existingRule);
    }

    @Test
    public void updateRule_shouldThrowException_whenIdDoesNotExist() {
      when(ruleRepository.findById(99)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> ruleService.updateRule(99, new RuleDto()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Rule not found");
    }
  }

  @Nested
  public class TestDeleteRule {

    @Test
    public void deleteRule_shouldDeleteRule() {
      Rule existingRule = new Rule();
      when(ruleRepository.findById(1)).thenReturn(Optional.of(existingRule));

      ruleService.deleteRule(1);

      verify(ruleRepository).findById(1);
      verify(ruleRepository).delete(existingRule);
    }

    @Test
    public void deleteRule_shouldThrowException_whenRuleNotFound() {
      when(ruleRepository.findById(99)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> ruleService.deleteRule(99))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Rule not found");
    }
  }
}
