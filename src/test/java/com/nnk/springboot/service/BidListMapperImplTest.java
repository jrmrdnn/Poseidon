package com.nnk.springboot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDto;
import org.junit.jupiter.api.Test;

class BidListMapperImplTest {

  private final BidListMapperImpl mapper = new BidListMapperImpl();

  @Test
  void bidListDtoToBidList_shouldReturnNull_whenDtoIsNull() {
    assertNull(mapper.bidListDtoToBidList((BidListDto) null));
  }

  @Test
  void bidListDtoToBidList_shouldMapCorrectly_whenDtoIsValid() {
    BidListDto dto = new BidListDto();
    dto.setAccount("Test Account");
    dto.setType("Test Type");
    dto.setQuantity(100.0);

    BidList result = mapper.bidListDtoToBidList(dto);

    assertNotNull(result);
    assertEquals(dto.getAccount(), result.getAccount());
    assertEquals(dto.getType(), result.getType());
    assertEquals(dto.getQuantity(), result.getBidQuantity());
  }

  @Test
  void overloadedBidListDtoToBidList_shouldReturnNull_whenBothIdAndDtoAreNull() {
    assertNull(mapper.bidListDtoToBidList(null, null));
  }

  @Test
  void overloadedBidListDtoToBidList_shouldSetIdOnly_whenDtoIsNull() {
    Integer id = 1;

    BidList result = mapper.bidListDtoToBidList(id, null);

    assertNotNull(result);
    assertEquals(id, result.getBidListId());
    assertNull(result.getAccount());
    assertNull(result.getType());
    assertNull(result.getBidQuantity());
  }

  @Test
  void overloadedBidListDtoToBidList_shouldMapCorrectly_whenIdIsNullButDtoIsValid() {
    BidListDto dto = new BidListDto();
    dto.setAccount("Test Account");
    dto.setType("Test Type");
    dto.setQuantity(100.0);

    BidList result = mapper.bidListDtoToBidList(null, dto);

    assertNotNull(result);
    assertNull(result.getBidListId());
    assertEquals(dto.getAccount(), result.getAccount());
    assertEquals(dto.getType(), result.getType());
    assertEquals(dto.getQuantity(), result.getBidQuantity());
  }

  @Test
  void overloadedBidListDtoToBidList_shouldMapCorrectly_whenBothIdAndDtoAreValid() {
    Integer id = 1;
    BidListDto dto = new BidListDto();
    dto.setAccount("Test Account");
    dto.setType("Test Type");
    dto.setQuantity(100.0);

    BidList result = mapper.bidListDtoToBidList(id, dto);

    assertNotNull(result);
    assertEquals(id, result.getBidListId());
    assertEquals(dto.getAccount(), result.getAccount());
    assertEquals(dto.getType(), result.getType());
    assertEquals(dto.getQuantity(), result.getBidQuantity());
  }

  @Test
  void bidListToBidListDto_shouldReturnNull_whenEntityIsNull() {
    assertNull(mapper.bidListToBidListDto(null));
  }

  @Test
  void bidListToBidListDto_shouldMapCorrectly_whenEntityIsValid() {
    BidList entity = new BidList();
    entity.setBidListId(1);
    entity.setAccount("Test Account");
    entity.setType("Test Type");
    entity.setBidQuantity(100.0);

    BidListDto result = mapper.bidListToBidListDto(entity);

    assertNotNull(result);
    assertEquals(entity.getAccount(), result.getAccount());
    assertEquals(entity.getType(), result.getType());
    assertEquals(entity.getBidQuantity(), result.getQuantity());
  }
}
