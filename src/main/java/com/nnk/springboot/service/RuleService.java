package com.nnk.springboot.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.dto.RuleDto;
import com.nnk.springboot.repository.RuleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RuleService {

  private final RuleRepository ruleRepository;
  private final RuleMapper ruleMapper;

  /**
   * Get all rules
   * @return List of all rules
   */
  public List<Rule> getAllRules() {
    return ruleRepository.findAll();
  }

  /**
   * Get a rule by its ID
   * @param id the ID of the rule name
   * @return the rule name with the specified ID
   */
  public RuleDto getRuleById(Integer id) {
    Rule rule = ruleRepository
      .findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Rule not found"));
    return ruleMapper.ruleToRuleDto(rule);
  }

  /**
   * Add a new rule
   * @param ruleDto the rule data
   */
  @Transactional
  public void addRule(RuleDto ruleDto) {
    Rule rule = ruleMapper.ruleDtoToRule(ruleDto);
    ruleRepository.save(rule);
  }

  /**
   * Update a rule
   * @param id the ID of the rule to update
   * @param ruleDto the updated rule data
   */
  @Transactional
  public void updateRule(Integer id, RuleDto ruleDto) {
    ruleRepository
      .findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Rule not found"));
    Rule rule = ruleMapper.ruleDtoToRule(id, ruleDto);
    ruleRepository.save(rule);
  }

  /**
   * Delete a rule
   * @param id the ID of the rule to delete
   */
  @Transactional
  public void deleteRule(Integer id) {
    Rule rule = ruleRepository
      .findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Rule not found"));
    ruleRepository.delete(rule);
  }
}
