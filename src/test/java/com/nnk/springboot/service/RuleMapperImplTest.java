package com.nnk.springboot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.dto.RuleDto;
import org.junit.jupiter.api.Test;

class RuleMapperImplTest {

  private final RuleMapperImpl mapper = new RuleMapperImpl();

  @Test
  void ruleDtoToRule_shouldReturnNull_whenDtoIsNull() {
    assertNull(mapper.ruleDtoToRule((RuleDto) null));
  }

  @Test
  void ruleDtoToRule_shouldMapCorrectly_whenDtoIsValid() {
    RuleDto dto = new RuleDto();
    dto.setName("Test Name");
    dto.setDescription("Test Description");
    dto.setJson("Test Json");
    dto.setTemplate("Test Template");
    dto.setSqlStr("Test SQL String");
    dto.setSqlPart("Test SQL Part");

    Rule result = mapper.ruleDtoToRule(dto);

    assertNotNull(result);
    assertEquals(dto.getName(), result.getName());
    assertEquals(dto.getDescription(), result.getDescription());
    assertEquals(dto.getJson(), result.getJson());
    assertEquals(dto.getTemplate(), result.getTemplate());
    assertEquals(dto.getSqlStr(), result.getSqlStr());
    assertEquals(dto.getSqlPart(), result.getSqlPart());
  }

  @Test
  void overloadedRuleDtoToRule_shouldReturnNull_whenBothIdAndDtoAreNull() {
    assertNull(mapper.ruleDtoToRule(null, null));
  }

  @Test
  void overloadedRuleDtoToRule_shouldSetIdOnly_whenDtoIsNull() {
    Integer id = 1;

    Rule result = mapper.ruleDtoToRule(id, null);

    assertNotNull(result);
    assertEquals(id, result.getId());
    assertNull(result.getName());
    assertNull(result.getDescription());
    assertNull(result.getJson());
    assertNull(result.getTemplate());
    assertNull(result.getSqlStr());
    assertNull(result.getSqlPart());
  }

  @Test
  void overloadedRuleDtoToRule_shouldMapCorrectly_whenIdIsNullButDtoIsValid() {
    RuleDto dto = new RuleDto();
    dto.setName("Test Name");
    dto.setDescription("Test Description");
    dto.setJson("Test Json");
    dto.setTemplate("Test Template");
    dto.setSqlStr("Test SQL String");
    dto.setSqlPart("Test SQL Part");

    Rule result = mapper.ruleDtoToRule(null, dto);

    assertNotNull(result);
    assertNull(result.getId());
    assertEquals(dto.getName(), result.getName());
    assertEquals(dto.getDescription(), result.getDescription());
    assertEquals(dto.getJson(), result.getJson());
    assertEquals(dto.getTemplate(), result.getTemplate());
    assertEquals(dto.getSqlStr(), result.getSqlStr());
    assertEquals(dto.getSqlPart(), result.getSqlPart());
  }

  @Test
  void overloadedRuleDtoToRule_shouldMapCorrectly_whenBothIdAndDtoAreValid() {
    Integer id = 1;
    RuleDto dto = new RuleDto();
    dto.setName("Test Name");
    dto.setDescription("Test Description");
    dto.setJson("Test Json");
    dto.setTemplate("Test Template");
    dto.setSqlStr("Test SQL String");
    dto.setSqlPart("Test SQL Part");

    Rule result = mapper.ruleDtoToRule(id, dto);

    assertNotNull(result);
    assertEquals(id, result.getId());
    assertEquals(dto.getName(), result.getName());
    assertEquals(dto.getDescription(), result.getDescription());
    assertEquals(dto.getJson(), result.getJson());
    assertEquals(dto.getTemplate(), result.getTemplate());
    assertEquals(dto.getSqlStr(), result.getSqlStr());
    assertEquals(dto.getSqlPart(), result.getSqlPart());
  }

  @Test
  void ruleToRuleDto_shouldReturnNull_whenEntityIsNull() {
    assertNull(mapper.ruleToRuleDto(null));
  }

  @Test
  void ruleToRuleDto_shouldMapCorrectly_whenEntityIsValid() {
    Rule entity = new Rule();
    entity.setId(1);
    entity.setName("Test Name");
    entity.setDescription("Test Description");
    entity.setJson("Test Json");
    entity.setTemplate("Test Template");
    entity.setSqlStr("Test SQL String");
    entity.setSqlPart("Test SQL Part");

    RuleDto result = mapper.ruleToRuleDto(entity);

    assertNotNull(result);
    assertEquals(entity.getName(), result.getName());
    assertEquals(entity.getDescription(), result.getDescription());
    assertEquals(entity.getJson(), result.getJson());
    assertEquals(entity.getTemplate(), result.getTemplate());
    assertEquals(entity.getSqlStr(), result.getSqlStr());
    assertEquals(entity.getSqlPart(), result.getSqlPart());
  }
}
