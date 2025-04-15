package com.nnk.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nnk.springboot.domain.Rule;

public interface RuleRepository extends JpaRepository<Rule, Integer> {}
