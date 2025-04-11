package com.nnk.springboot.domain;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table
@Data
public class BidList {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer bidListId;

  @NotBlank(message = "Account is mandatory")
  @Column(nullable = false, length = 30)
  private String account;

  @NotBlank(message = "Type is mandatory")
  @Column(nullable = false, length = 30)
  private String type;

  @NotNull(message = "Bid quantity is mandatory")
  @Column(nullable = false)
  private Double bidQuantity;

  private Double askQuantity;

  private Double bid;

  private Double ask;

  @Column(length = 125)
  private String benchmark;

  private Timestamp bidListDate;

  @Column(length = 125)
  private String commentary;

  @Column(length = 125)
  private String security;

  @Column(length = 10)
  private String status;

  @Column(length = 125)
  private String trader;

  @Column(length = 125)
  private String book;

  @Column(length = 125)
  private String creationName;

  private Timestamp creationDate;

  @Column(length = 125)
  private String revisionName;

  private Timestamp revisionDate;

  @Column(length = 125)
  private String dealName;

  @Column(length = 125)
  private String dealType;

  @Column(length = 125)
  private String sourceListId;

  @Column(length = 125)
  private String side;
}
