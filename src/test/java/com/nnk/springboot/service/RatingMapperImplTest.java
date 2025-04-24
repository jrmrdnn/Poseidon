package com.nnk.springboot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDto;
import org.junit.jupiter.api.Test;

class RatingMapperImplTest {

  private final RatingMapperImpl mapper = new RatingMapperImpl();

  @Test
  void ratingDtoToRating_shouldReturnNull_whenDtoIsNull() {
    assertNull(mapper.ratingDtoToRating((RatingDto) null));
  }

  @Test
  void ratingDtoToRating_shouldMapCorrectly_whenDtoIsValid() {
    RatingDto dto = new RatingDto();
    dto.setMoodys("Moodys Test");
    dto.setSand("Sand Test");
    dto.setFitch("Fitch Test");
    dto.setOrder(10);

    Rating result = mapper.ratingDtoToRating(dto);

    assertNotNull(result);
    assertEquals(dto.getMoodys(), result.getMoodysRating());
    assertEquals(dto.getSand(), result.getSandPRating());
    assertEquals(dto.getFitch(), result.getFitchRating());
    assertEquals(dto.getOrder(), result.getOrderNumber());
  }

  @Test
  void overloadedRatingDtoToRating_shouldReturnNull_whenBothIdAndDtoAreNull() {
    assertNull(mapper.ratingDtoToRating(null, null));
  }

  @Test
  void overloadedRatingDtoToRating_shouldSetIdOnly_whenDtoIsNull() {
    Integer id = 1;

    Rating result = mapper.ratingDtoToRating(id, null);

    assertNotNull(result);
    assertEquals(id, result.getId());
    assertNull(result.getMoodysRating());
    assertNull(result.getSandPRating());
    assertNull(result.getFitchRating());
    assertNull(result.getOrderNumber());
  }

  @Test
  void overloadedRatingDtoToRating_shouldMapCorrectly_whenIdIsNullButDtoIsValid() {
    RatingDto dto = new RatingDto();
    dto.setMoodys("Moodys Test");
    dto.setSand("Sand Test");
    dto.setFitch("Fitch Test");
    dto.setOrder(10);

    Rating result = mapper.ratingDtoToRating(null, dto);

    assertNotNull(result);
    assertNull(result.getId());
    assertEquals(dto.getMoodys(), result.getMoodysRating());
    assertEquals(dto.getSand(), result.getSandPRating());
    assertEquals(dto.getFitch(), result.getFitchRating());
    assertEquals(dto.getOrder(), result.getOrderNumber());
  }

  @Test
  void overloadedRatingDtoToRating_shouldMapCorrectly_whenBothIdAndDtoAreValid() {
    Integer id = 1;
    RatingDto dto = new RatingDto();
    dto.setMoodys("Moodys Test");
    dto.setSand("Sand Test");
    dto.setFitch("Fitch Test");
    dto.setOrder(10);

    Rating result = mapper.ratingDtoToRating(id, dto);

    assertNotNull(result);
    assertEquals(id, result.getId());
    assertEquals(dto.getMoodys(), result.getMoodysRating());
    assertEquals(dto.getSand(), result.getSandPRating());
    assertEquals(dto.getFitch(), result.getFitchRating());
    assertEquals(dto.getOrder(), result.getOrderNumber());
  }

  @Test
  void ratingToRatingDto_shouldReturnNull_whenEntityIsNull() {
    assertNull(mapper.ratingToRatingDto(null));
  }

  @Test
  void ratingToRatingDto_shouldMapCorrectly_whenEntityIsValid() {
    Rating entity = new Rating();
    entity.setId(1);
    entity.setMoodysRating("Moodys Test");
    entity.setSandPRating("Sand Test");
    entity.setFitchRating("Fitch Test");
    entity.setOrderNumber(10);

    RatingDto result = mapper.ratingToRatingDto(entity);

    assertNotNull(result);
    assertEquals(entity.getMoodysRating(), result.getMoodys());
    assertEquals(entity.getSandPRating(), result.getSand());
    assertEquals(entity.getFitchRating(), result.getFitch());
    assertEquals(entity.getOrderNumber(), result.getOrder());
  }
}
