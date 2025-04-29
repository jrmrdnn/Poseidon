package com.nnk.springboot.domain;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;

public class TradeTest {

  @Test
  public void testOnCreate() {
    Trade trade = new Trade();
    LocalDateTime beforeCreation = LocalDateTime.now();

    trade.onCreate();

    assertNotNull(trade.getCreationDate());
    LocalDateTime creationDate = trade.getCreationDate();
    assertTrue(
      creationDate.isAfter(beforeCreation) ||
      creationDate.isEqual(beforeCreation)
    );
    assertTrue(ChronoUnit.SECONDS.between(beforeCreation, creationDate) < 2);
  }

  @Test
  public void testOnUpdate() {
    Trade trade = new Trade();
    LocalDateTime oldRevisionDate = LocalDateTime.now().minusDays(1);
    trade.setRevisionDate(oldRevisionDate);
    LocalDateTime beforeUpdate = LocalDateTime.now();

    trade.onUpdate();

    assertNotNull(trade.getRevisionDate());
    assertNotEquals(oldRevisionDate, trade.getRevisionDate());
    LocalDateTime newRevisionDate = trade.getRevisionDate();
    assertTrue(
      newRevisionDate.isAfter(beforeUpdate) ||
      newRevisionDate.isEqual(beforeUpdate)
    );
    assertTrue(ChronoUnit.SECONDS.between(beforeUpdate, newRevisionDate) < 2);
  }
}
