package com.nnk.springboot.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDto;
import com.nnk.springboot.repository.RatingRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RatingService {

  private final RatingRepository ratingRepository;
  private final RatingMapper ratingMapper;

  /**
   * Get all ratings
   *
   * @return List of all ratings
   */
  public List<Rating> getAllRatings() {
    return ratingRepository.findAll();
  }

  /**
   * Get a rating by its ID
   *
   * @param id the ID of the rating
   * @return the rating with the specified ID
   */
  public RatingDto getRatingById(Integer id) {
    Rating rating = ratingRepository
      .findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Rating not found"));
    return ratingMapper.ratingToRatingDto(rating);
  }

  /**
   * Add a new rating
   *
   * @param ratingDto the rating data
   */
  @Transactional
  public void addRating(RatingDto ratingDto) {
    Rating rating = ratingMapper.ratingDtoToRating(ratingDto);
    ratingRepository.save(rating);
  }

  /**
   * Update a rating
   *
   * @param id the ID of the rating to update
   * @param updatedRating the updated rating data
   */
  @Transactional
  public void updateRating(Integer id, RatingDto ratingDto) {
    ratingRepository
      .findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Rating not found"));
    Rating rating = ratingMapper.ratingDtoToRating(id, ratingDto);
    ratingRepository.save(rating);
  }

  /**
   * Delete a rating
   *
   * @param id the ID of the rating to delete
   */
  @Transactional
  public void deleteRating(Integer id) {
    Rating rating = ratingRepository
      .findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Rating not found"));
    ratingRepository.delete(rating);
  }
}
