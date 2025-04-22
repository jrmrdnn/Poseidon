package com.nnk.springboot.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

@ExtendWith(MockitoExtension.class)
public class HomeControllerTest {

  @Mock
  private Model model;

  @InjectMocks
  private HomeController homeController;

  @Nested
  public class TestHome {

    @Test
    public void home_shouldReturnHomeView() {
      String view = homeController.home(model);

      assertThat(view).isEqualTo("home");
    }
  }

  @Nested
  public class TestAdminHome {

    @Test
    public void adminHome_shouldRedirectToBidListView() {
      String view = homeController.adminHome(model);

      assertThat(view).isEqualTo("redirect:/bidList/list");
    }
  }
}
