package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * RatingMapper is an interface for mapping between Rating and RatingDto objects.
 */
@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RatingMapper {
  @Mapping(target = "moodysRating", source = "ratingDto.moodys")
  @Mapping(target = "sandPRating", source = "ratingDto.sand")
  @Mapping(target = "fitchRating", source = "ratingDto.fitch")
  @Mapping(target = "orderNumber", source = "ratingDto.order")
  Rating ratingDtoToRating(RatingDto ratingDto);

  @Mapping(target = "moodys", source = "rating.moodysRating")
  @Mapping(target = "sand", source = "rating.sandPRating")
  @Mapping(target = "fitch", source = "rating.fitchRating")
  @Mapping(target = "order", source = "rating.orderNumber")
  RatingDto ratingToRatingDto(Rating rating);

  @Mapping(target = "moodysRating", source = "ratingDto.moodys")
  @Mapping(target = "sandPRating", source = "ratingDto.sand")
  @Mapping(target = "fitchRating", source = "ratingDto.fitch")
  @Mapping(target = "orderNumber", source = "ratingDto.order")
  Rating ratingDtoToRating(Integer id, RatingDto ratingDto);
}
