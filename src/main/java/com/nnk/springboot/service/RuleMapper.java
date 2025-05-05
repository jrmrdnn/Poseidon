package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.dto.RuleDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * RuleMapper is an interface for mapping between Rule and RuleDto objects.
 */
@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RuleMapper {
  Rule ruleDtoToRule(RuleDto ruleDto);

  RuleDto ruleToRuleDto(Rule rule);

  Rule ruleDtoToRule(Integer id, RuleDto ruleDto);
}
