package com.nnk.springboot.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class CurvePoint {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private Integer curveId;

  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime asOfDate;

  @Column(nullable = false)
  private Double term;

  @Column(nullable = false)
  private Double value;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime creationDate;

  @PrePersist
  protected void onCreate() {
    creationDate = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    creationDate = LocalDateTime.now();
  }
}
