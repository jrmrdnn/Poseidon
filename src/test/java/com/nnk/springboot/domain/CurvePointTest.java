package com.nnk.springboot.domain;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;

public class CurvePointTest {

  @Test
  public void testOnCreate() {
    CurvePoint curvePoint = new CurvePoint();
    LocalDateTime beforeCreation = LocalDateTime.now();

    curvePoint.onCreate();

    assertNotNull(curvePoint.getCreationDate());
    LocalDateTime creationDate = curvePoint.getCreationDate();
    assertTrue(
      creationDate.isAfter(beforeCreation) ||
      creationDate.isEqual(beforeCreation)
    );
    assertTrue(ChronoUnit.SECONDS.between(beforeCreation, creationDate) < 2);
  }

  @Test
  public void testOnUpdate() {
    CurvePoint curvePoint = new CurvePoint();
    curvePoint.setCreationDate(LocalDateTime.now().minusDays(1));
    LocalDateTime oldCreationDate = curvePoint.getCreationDate();
    LocalDateTime beforeUpdate = LocalDateTime.now();

    curvePoint.onUpdate();

    assertNotNull(curvePoint.getCreationDate());
    assertNotEquals(oldCreationDate, curvePoint.getCreationDate());
    LocalDateTime newCreationDate = curvePoint.getCreationDate();
    assertTrue(
      newCreationDate.isAfter(beforeUpdate) ||
      newCreationDate.isEqual(beforeUpdate)
    );
    assertTrue(ChronoUnit.SECONDS.between(beforeUpdate, newCreationDate) < 2);
  }
}
