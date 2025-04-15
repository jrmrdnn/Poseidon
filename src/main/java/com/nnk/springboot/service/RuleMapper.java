package com.nnk.springboot.service;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.dto.RuleDto;

@Mapper(componentModel = "spring")
public interface RuleMapper {
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "name", source = "ruleDto.name")
  @Mapping(target = "description", source = "ruleDto.description")
  @Mapping(target = "json", source = "ruleDto.json")
  @Mapping(target = "template", source = "ruleDto.template")
  @Mapping(target = "sqlStr", source = "ruleDto.sqlStr")
  @Mapping(target = "sqlPart", source = "ruleDto.sqlPart")
  Rule ruleDtoToRule(RuleDto ruleDto);

  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "ruleDto.name")
  @Mapping(target = "description", source = "ruleDto.description")
  @Mapping(target = "json", source = "ruleDto.json")
  @Mapping(target = "template", source = "ruleDto.template")
  @Mapping(target = "sqlStr", source = "ruleDto.sqlStr")
  @Mapping(target = "sqlPart", source = "ruleDto.sqlPart")
  Rule ruleDtoToRule(Integer id, RuleDto ruleDto);

  @Mapping(target = "name", source = "rule.name")
  @Mapping(target = "description", source = "rule.description")
  @Mapping(target = "json", source = "rule.json")
  @Mapping(target = "template", source = "rule.template")
  @Mapping(target = "sqlStr", source = "rule.sqlStr")
  @Mapping(target = "sqlPart", source = "rule.sqlPart")
  RuleDto ruleToRuleDto(Rule rule);
}
