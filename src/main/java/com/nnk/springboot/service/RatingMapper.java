package com.nnk.springboot.service;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDto;

@Mapper(componentModel = "spring")
public interface RatingMapper {
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "moodysRating", source = "ratingDto.moodys")
  @Mapping(target = "sandPRating", source = "ratingDto.sand")
  @Mapping(target = "fitchRating", source = "ratingDto.fitch")
  @Mapping(target = "orderNumber", source = "ratingDto.order")
  Rating ratingDtoToRating(RatingDto ratingDto);

  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  @Mapping(target = "moodysRating", source = "ratingDto.moodys")
  @Mapping(target = "sandPRating", source = "ratingDto.sand")
  @Mapping(target = "fitchRating", source = "ratingDto.fitch")
  @Mapping(target = "orderNumber", source = "ratingDto.order")
  Rating ratingDtoToRating(Integer id, RatingDto ratingDto);

  @Mapping(target = "moodys", source = "rating.moodysRating")
  @Mapping(target = "sand", source = "rating.sandPRating")
  @Mapping(target = "fitch", source = "rating.fitchRating")
  @Mapping(target = "order", source = "rating.orderNumber")
  RatingDto ratingToRatingDto(Rating rating);
}
