package com.nnk.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.springboot.domain.Rating;

public interface RatingRepository extends JpaRepository<Rating, Integer> {}
