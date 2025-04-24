package com.nnk.springboot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDto;
import org.junit.jupiter.api.Test;

class TradeMapperImplTest {

  private final TradeMapperImpl mapper = new TradeMapperImpl();

  @Test
  void tradeDtoToTrade_shouldReturnNull_whenDtoIsNull() {
    assertNull(mapper.tradeDtoToTrade((TradeDto) null));
  }

  @Test
  void tradeDtoToTrade_shouldMapCorrectly_whenDtoIsValid() {
    TradeDto dto = new TradeDto();
    dto.setAccount("Test Account");
    dto.setType("Test Type");
    dto.setQuantity(100.0);

    Trade result = mapper.tradeDtoToTrade(dto);

    assertNotNull(result);
    assertEquals(dto.getAccount(), result.getAccount());
    assertEquals(dto.getType(), result.getType());
    assertEquals(dto.getQuantity(), result.getBuyQuantity());
  }

  @Test
  void overloadedTradeDtoToTrade_shouldReturnNull_whenBothIdAndDtoAreNull() {
    assertNull(mapper.tradeDtoToTrade(null, null));
  }

  @Test
  void overloadedTradeDtoToTrade_shouldSetIdOnly_whenDtoIsNull() {
    Integer id = 1;

    Trade result = mapper.tradeDtoToTrade(id, null);

    assertNotNull(result);
    assertEquals(id, result.getTradeId());
    assertNull(result.getAccount());
    assertNull(result.getType());
    assertNull(result.getBuyQuantity());
  }

  @Test
  void overloadedTradeDtoToTrade_shouldMapCorrectly_whenIdIsNullButDtoIsValid() {
    TradeDto dto = new TradeDto();
    dto.setAccount("Test Account");
    dto.setType("Test Type");
    dto.setQuantity(100.0);

    Trade result = mapper.tradeDtoToTrade(null, dto);

    assertNotNull(result);
    assertNull(result.getTradeId());
    assertEquals(dto.getAccount(), result.getAccount());
    assertEquals(dto.getType(), result.getType());
    assertEquals(dto.getQuantity(), result.getBuyQuantity());
  }

  @Test
  void overloadedTradeDtoToTrade_shouldMapCorrectly_whenBothIdAndDtoAreValid() {
    Integer id = 1;
    TradeDto dto = new TradeDto();
    dto.setAccount("Test Account");
    dto.setType("Test Type");
    dto.setQuantity(100.0);

    Trade result = mapper.tradeDtoToTrade(id, dto);

    assertNotNull(result);
    assertEquals(id, result.getTradeId());
    assertEquals(dto.getAccount(), result.getAccount());
    assertEquals(dto.getType(), result.getType());
    assertEquals(dto.getQuantity(), result.getBuyQuantity());
  }

  @Test
  void tradeToTradeDto_shouldReturnNull_whenEntityIsNull() {
    assertNull(mapper.tradeToTradeDto(null));
  }

  @Test
  void tradeToTradeDto_shouldMapCorrectly_whenEntityIsValid() {
    Trade entity = new Trade();
    entity.setTradeId(1);
    entity.setAccount("Test Account");
    entity.setType("Test Type");
    entity.setBuyQuantity(100.0);

    TradeDto result = mapper.tradeToTradeDto(entity);

    assertNotNull(result);
    assertEquals(entity.getAccount(), result.getAccount());
    assertEquals(entity.getType(), result.getType());
    assertEquals(entity.getBuyQuantity(), result.getQuantity());
  }
}
