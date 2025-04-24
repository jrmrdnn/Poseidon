package com.nnk.springboot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;
import org.junit.jupiter.api.Test;

class CurvePointMapperImplTest {

  private final CurvePointMapperImpl mapper = new CurvePointMapperImpl();

  @Test
  void curvePointDtoToCurvePoint_shouldReturnNull_whenDtoIsNull() {
    assertNull(mapper.curvePointDtoToCurvePoint((CurvePointDto) null));
  }

  @Test
  void curvePointDtoToCurvePoint_shouldMapCorrectly_whenDtoIsValid() {
    CurvePointDto dto = new CurvePointDto();
    dto.setCurveId(5);
    dto.setTerm(10.0);
    dto.setValue(20.0);

    CurvePoint result = mapper.curvePointDtoToCurvePoint(dto);

    assertNotNull(result);
    assertEquals(dto.getCurveId(), result.getCurveId());
    assertEquals(dto.getTerm(), result.getTerm());
    assertEquals(dto.getValue(), result.getValue());
  }

  @Test
  void overloadedCurvePointDtoToCurvePoint_shouldReturnNull_whenBothIdAndDtoAreNull() {
    assertNull(mapper.curvePointDtoToCurvePoint(null, null));
  }

  @Test
  void overloadedCurvePointDtoToCurvePoint_shouldSetIdOnly_whenDtoIsNull() {
    Integer id = 1;

    CurvePoint result = mapper.curvePointDtoToCurvePoint(id, null);

    assertNotNull(result);
    assertEquals(id, result.getId());
    assertNull(result.getCurveId());
    assertNull(result.getTerm());
    assertNull(result.getValue());
  }

  @Test
  void overloadedCurvePointDtoToCurvePoint_shouldMapCorrectly_whenIdIsNullButDtoIsValid() {
    CurvePointDto dto = new CurvePointDto();
    dto.setCurveId(5);
    dto.setTerm(10.0);
    dto.setValue(20.0);

    CurvePoint result = mapper.curvePointDtoToCurvePoint(null, dto);

    assertNotNull(result);
    assertNull(result.getId());
    assertEquals(dto.getCurveId(), result.getCurveId());
    assertEquals(dto.getTerm(), result.getTerm());
    assertEquals(dto.getValue(), result.getValue());
  }

  @Test
  void overloadedCurvePointDtoToCurvePoint_shouldMapCorrectly_whenBothIdAndDtoAreValid() {
    Integer id = 1;
    CurvePointDto dto = new CurvePointDto();
    dto.setCurveId(5);
    dto.setTerm(10.0);
    dto.setValue(20.0);

    CurvePoint result = mapper.curvePointDtoToCurvePoint(id, dto);

    assertNotNull(result);
    assertEquals(id, result.getId());
    assertEquals(dto.getCurveId(), result.getCurveId());
    assertEquals(dto.getTerm(), result.getTerm());
    assertEquals(dto.getValue(), result.getValue());
  }

  @Test
  void curvePointToCurvePointDto_shouldReturnNull_whenEntityIsNull() {
    assertNull(mapper.curvePointToCurvePointDto(null));
  }

  @Test
  void curvePointToCurvePointDto_shouldMapCorrectly_whenEntityIsValid() {
    CurvePoint entity = new CurvePoint();
    entity.setId(1);
    entity.setCurveId(5);
    entity.setTerm(10.0);
    entity.setValue(20.0);

    CurvePointDto result = mapper.curvePointToCurvePointDto(entity);

    assertNotNull(result);
    assertEquals(entity.getCurveId(), result.getCurveId());
    assertEquals(entity.getTerm(), result.getTerm());
    assertEquals(entity.getValue(), result.getValue());
  }
}
