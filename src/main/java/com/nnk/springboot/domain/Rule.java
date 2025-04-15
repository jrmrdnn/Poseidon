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
public class Rule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, length = 125)
  private String name;

  @Column(nullable = false, length = 125)
  private String description;

  @Column(nullable = false, length = 125)
  private String json;

  @Column(nullable = false, length = 512)
  private String template;

  @Column(nullable = false, length = 125)
  private String sqlStr;

  @Column(nullable = false, length = 125)
  private String sqlPart;
}
