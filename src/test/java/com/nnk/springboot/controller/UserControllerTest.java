package com.nnk.springboot.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.service.UserService;
import java.util.Collections;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  @Mock
  private UserService userService;

  @Mock
  private Model model;

  @Mock
  private RedirectAttributes redirectAttributes;

  @Mock
  private BindingResult bindingResult;

  @InjectMocks
  private UserController userController;

  @Nested
  public class TestShowUser {

    @Test
    public void showUser_shouldAddUsersToModel_andReturnListView() {
      User user = new User();
      when(userService.getAllUsers()).thenReturn(
        Collections.singletonList(user)
      );

      String view = userController.showUser(redirectAttributes, model);

      verify(model).addAttribute("users", Collections.singletonList(user));
      assertThat(view).isEqualTo("user/list");
    }

    @Test
    public void showUser_shouldHandleException_andReturnListView() {
      when(userService.getAllUsers()).thenThrow(new RuntimeException());

      String view = userController.showUser(redirectAttributes, model);

      assertThat(view).isEqualTo("user/list");
    }
  }

  @Nested
  public class TestShowAddUserForm {

    @Test
    public void showAddUserForm_shouldReturnAddView() {
      String view = userController.showAddUserForm(model);

      verify(model).addAttribute(eq("userDto"), any(UserDto.class));
      assertThat(view).isEqualTo("user/add");
    }
  }

  @Nested
  public class TestShowUpdateForm {

    @Test
    public void showUpdateForm_shouldAddUserToModel_andReturnUpdateView() {
      UserDto userDto = new UserDto();
      when(userService.getUserById(1)).thenReturn(userDto);

      String view = userController.showUpdateForm(1, redirectAttributes, model);

      verify(model).addAttribute("userDto", userDto);
      verify(model).addAttribute("userId", 1);
      assertThat(view).isEqualTo("user/update");
    }

    @Test
    public void showUpdateForm_shouldHandleException_andRedirect() {
      when(userService.getUserById(1)).thenThrow(new RuntimeException());

      String view = userController.showUpdateForm(1, redirectAttributes, model);

      assertThat(view).isEqualTo("redirect:/user/list");
    }
  }

  @Nested
  public class TestValidateUser {

    @Test
    public void validateUser_shouldReturnAddView_whenBindingResultHasErrors() {
      UserDto userDto = new UserDto();
      when(bindingResult.hasErrors()).thenReturn(true);

      String view = userController.validateUser(
        userDto,
        bindingResult,
        redirectAttributes,
        model
      );

      assertThat(view).isEqualTo("user/add");
      verifyNoInteractions(userService);
    }

    @Test
    public void validateUser_shouldAddUser_andRedirect_whenNoErrors() {
      UserDto userDto = new UserDto();
      when(bindingResult.hasErrors()).thenReturn(false);

      String view = userController.validateUser(
        userDto,
        bindingResult,
        redirectAttributes,
        model
      );

      verify(userService).addUser(any(UserDto.class));
      assertThat(view).isEqualTo("redirect:/user/list");
    }

    @Test
    public void validateUser_shouldHandleException_andRedirect() {
      UserDto userDto = new UserDto();
      when(bindingResult.hasErrors()).thenReturn(false);
      doThrow(new RuntimeException())
        .when(userService)
        .addUser(any(UserDto.class));

      String view = userController.validateUser(
        userDto,
        bindingResult,
        redirectAttributes,
        model
      );

      verify(userService).addUser(any(UserDto.class));
      assertThat(view).isEqualTo("redirect:/user/add");
    }
  }

  @Nested
  public class TestUpdateUser {

    @Test
    public void updateUser_shouldReturnUpdateView_whenBindingResultHasErrors() {
      UserDto userDto = new UserDto();
      when(bindingResult.hasErrors()).thenReturn(true);

      String view = userController.updateUser(
        1,
        userDto,
        bindingResult,
        redirectAttributes,
        model
      );

      assertThat(view).isEqualTo("user/update");
      verifyNoInteractions(userService);
    }

    @Test
    public void updateUser_shouldUpdateUser_andRedirect_whenNoErrors() {
      UserDto userDto = new UserDto();
      when(bindingResult.hasErrors()).thenReturn(false);

      String view = userController.updateUser(
        1,
        userDto,
        bindingResult,
        redirectAttributes,
        model
      );

      verify(userService).updateUser(eq(1), any(UserDto.class));
      assertThat(view).isEqualTo("redirect:/user/list");
    }

    @Test
    public void updateUser_shouldHandleException_andRedirect() {
      UserDto userDto = new UserDto();
      when(bindingResult.hasErrors()).thenReturn(false);
      doThrow(new RuntimeException())
        .when(userService)
        .updateUser(eq(1), any(UserDto.class));

      String view = userController.updateUser(
        1,
        userDto,
        bindingResult,
        redirectAttributes,
        model
      );

      verify(userService).updateUser(eq(1), any(UserDto.class));
      assertThat(view).isEqualTo("redirect:/user/list");
    }
  }

  @Nested
  public class TestDeleteUser {

    @Test
    public void deleteUser_shouldDeleteUser_andRedirect() {
      String view = userController.deleteUser(1, redirectAttributes, model);

      verify(userService).deleteUser(1);
      assertThat(view).isEqualTo("redirect:/user/list");
    }

    @Test
    public void deleteUser_shouldHandleException_andRedirect() {
      doThrow(new RuntimeException()).when(userService).deleteUser(1);

      String view = userController.deleteUser(1, redirectAttributes, model);

      assertThat(view).isEqualTo("redirect:/user/list");
    }
  }
}
