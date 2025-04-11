package com.nnk.springboot;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class ApplicationTests {

  @Autowired
  private ApplicationContext applicationContext;

  @Test
  public void contextLoads() {
    assertNotNull(applicationContext);
  }
}
