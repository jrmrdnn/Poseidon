package com.nnk.springboot.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

  @Mock
  private RedirectAttributes redirectAttributes;

  @InjectMocks
  private AuthController authController;

  @Nested
  public class TestShowLogin {

    @Test
    public void showLogin_shouldReturnLoginView() {
      String view = authController.showLogin(redirectAttributes);

      assertThat(view).isEqualTo("login");
    }
  }
}
