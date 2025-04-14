package com.nnk.springboot.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDto;
import com.nnk.springboot.repository.RatingRepository;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

  @Mock
  private RatingRepository ratingRepository;

  @Mock
  private RatingMapper ratingMapper;

  @InjectMocks
  private RatingService ratingService;

  @Nested
  public class TestGetAllRatings {

    @Test
    public void getAllRatings_shouldReturnAllRatings() {
      List<Rating> ratings = Arrays.asList(new Rating(), new Rating());
      when(ratingRepository.findAll()).thenReturn(ratings);

      List<Rating> result = ratingService.getAllRatings();

      assertThat(result).hasSize(2);
      verify(ratingRepository).findAll();
    }

    @Test
    public void getAllRatings_shouldReturnEmptyList_whenNoRatings() {
      when(ratingRepository.findAll()).thenReturn(Collections.emptyList());

      List<Rating> result = ratingService.getAllRatings();

      assertThat(result).isEmpty();
      verify(ratingRepository).findAll();
    }
  }

  @Nested
  public class TestGetRatingById {

    @Test
    public void getRatingById_shouldReturnRating_whenIdExists() {
      Rating rating = new Rating();
      RatingDto ratingDto = new RatingDto();

      when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));
      when(ratingMapper.ratingToRatingDto(rating)).thenReturn(ratingDto);

      RatingDto result = ratingService.getRatingById(1);

      assertThat(result).isEqualTo(ratingDto);
      verify(ratingRepository).findById(1);
      verify(ratingMapper).ratingToRatingDto(rating);
    }

    @Test
    public void getRatingById_shouldThrowException_whenIdDoesNotExist() {
      when(ratingRepository.findById(99)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> ratingService.getRatingById(99))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Rating not found");
    }
  }

  @Nested
  public class TestAddRating {

    @Test
    public void addRating_shouldSaveNewRating() {
      RatingDto ratingDto = new RatingDto();
      Rating rating = new Rating();

      when(ratingMapper.ratingDtoToRating(ratingDto)).thenReturn(rating);

      ratingService.addRating(ratingDto);

      verify(ratingMapper).ratingDtoToRating(ratingDto);
      verify(ratingRepository).save(rating);
    }
  }

  @Nested
  public class TestUpdateRating {

    @Test
    public void updateRating_shouldUpdateExistingRating() {
      Rating existingRating = new Rating();
      RatingDto updatedRatingDto = new RatingDto();

      when(ratingRepository.findById(1)).thenReturn(
        Optional.of(existingRating)
      );
      when(ratingMapper.ratingDtoToRating(1, updatedRatingDto)).thenReturn(
        existingRating
      );

      ratingService.updateRating(1, updatedRatingDto);

      verify(ratingRepository).findById(1);
      verify(ratingMapper).ratingDtoToRating(1, updatedRatingDto);
      verify(ratingRepository).save(existingRating);
    }

    @Test
    public void updateRating_shouldThrowException_whenIdDoesNotExist() {
      when(ratingRepository.findById(99)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> ratingService.updateRating(99, new RatingDto()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Rating not found");
    }
  }

  @Nested
  public class TestDeleteRating {

    @Test
    public void deleteRating_shouldDeleteRating() {
      Rating existingRating = new Rating();
      when(ratingRepository.findById(1)).thenReturn(
        Optional.of(existingRating)
      );

      ratingService.deleteRating(1);

      verify(ratingRepository).findById(1);
      verify(ratingRepository).delete(existingRating);
    }

    @Test
    public void deleteRating_shouldThrowException_whenRatingNotFound() {
      when(ratingRepository.findById(99)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> ratingService.deleteRating(99))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Rating not found");
    }
  }
}
