package com.nnk.springboot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.security.Timestamp;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Table
@Data
public class Trade {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer tradeId;

  @Column(nullable = false, length = 30)
  private String account;

  @Column(nullable = false, length = 30)
  private String type;

  @Column(nullable = false)
  private Double buyQuantity;

  private Double sellQuantity;

  private Double buyPrice;

  private Double sellPrice;

  @Temporal(TemporalType.TIMESTAMP)
  private Timestamp tradeDate;

  @Column(length = 125)
  private String security;

  @Column(length = 10)
  private String status;

  @Column(length = 125)
  private String trader;

  @Column(length = 125)
  private String benchmark;

  @Column(length = 125)
  private String book;

  @Column(length = 125)
  private String creationName;

  @Column(nullable = false)
  private LocalDateTime creationDate;

  @Column(length = 125)
  private String revisionName;

  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime revisionDate;

  @Column(length = 125)
  private String dealName;

  @Column(length = 125)
  private String dealType;

  @Column(length = 125)
  private String sourceListId;

  @Column(length = 125)
  private String side;

  @PrePersist
  protected void onCreate() {
    creationDate = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    revisionDate = LocalDateTime.now();
  }
}
