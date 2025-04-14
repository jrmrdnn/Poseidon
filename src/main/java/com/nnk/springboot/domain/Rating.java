package com.nnk.springboot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class Rating {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, length = 125)
  private String moodysRating;

  @Column(nullable = false, length = 125)
  private String sandPRating;

  @Column(nullable = false, length = 125)
  private String fitchRating;

  @Column(nullable = false)
  private Integer orderNumber;
}
