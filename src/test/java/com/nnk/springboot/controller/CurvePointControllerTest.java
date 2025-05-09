package com.nnk.springboot.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.service.CurvePointService;
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
class CurvePointControllerTest {

  @Mock
  private CurvePointService curvePointService;

  @Mock
  private Model model;

  @Mock
  private RedirectAttributes redirectAttributes;

  @Mock
  private BindingResult bindingResult;

  @InjectMocks
  private CurvePointController curvePointController;

  @Nested
  public class TestShowCurvePoint {

    @Test
    public void showCurvePoint_shouldAddCurvePointsToModel_andReturnListView() {
      CurvePoint curvePoint = new CurvePoint();
      when(curvePointService.getAllCurvePoints()).thenReturn(
        Collections.singletonList(curvePoint)
      );

      String view = curvePointController.showCurvePoint(
        redirectAttributes,
        model
      );
      verify(model).addAttribute(
        "curvePoints",
        Collections.singletonList(curvePoint)
      );
      assertThat(view).isEqualTo("curvePoint/list");
    }

    @Test
    public void showCurvePoint_shouldHandleException_andReturnListView() {
      when(curvePointService.getAllCurvePoints()).thenThrow(
        new RuntimeException()
      );

      String view = curvePointController.showCurvePoint(
        redirectAttributes,
        model
      );

      assertThat(view).isEqualTo("curvePoint/list");
    }
  }

  @Nested
  public class TestShowAddCurvePointForm {

    @Test
    public void showAddCurvePointForm_shouldAddNewCurvePointToModel_andReturnAddView() {
      String view = curvePointController.showAddCurvePointForm(model);

      verify(model).addAttribute(eq("curvePointDto"), any(CurvePointDto.class));
      assertThat(view).isEqualTo("curvePoint/add");
    }
  }

  @Nested
  public class TestShowUpdateCurvePointForm {

    @Test
    public void showUpdateCurvePointForm_shouldAddCurvePointListToModel_andReturnUpdateView() {
      CurvePointDto curvePoint = new CurvePointDto();
      when(curvePointService.getCurvePointById(1)).thenReturn(curvePoint);

      String view = curvePointController.showUpdateCurvePointForm(
        1,
        redirectAttributes,
        model
      );

      verify(model).addAttribute("curvePointDto", curvePoint);
      assertThat(view).isEqualTo("curvePoint/update");
    }

    @Test
    public void showUpdateCurvePointForm_shouldHandleException_andReturnListView() {
      when(curvePointService.getCurvePointById(1)).thenThrow(
        new RuntimeException()
      );

      String view = curvePointController.showUpdateCurvePointForm(
        1,
        redirectAttributes,
        model
      );

      assertThat(view).isEqualTo("redirect:/curvePoint/list");
    }
  }

  @Nested
  public class TestDeleteCurvePoint {

    @Test
    public void deleteCurvePoint_shouldDeleteCurvePoint_andRedirect() {
      String view = curvePointController.deleteCurvePoint(
        1,
        redirectAttributes,
        model
      );

      verify(curvePointService).deleteCurvePoint(1);
      assertThat(view).isEqualTo("redirect:/curvePoint/list");
    }

    @Test
    public void deleteCurvePoint_shouldHandleException_andRedirect() {
      doThrow(new RuntimeException())
        .when(curvePointService)
        .deleteCurvePoint(1);

      String view = curvePointController.deleteCurvePoint(
        1,
        redirectAttributes,
        model
      );

      assertThat(view).isEqualTo("redirect:/curvePoint/list");
    }
  }

  @Nested
  public class TestValidateCurvePoint {

    @Test
    public void addCurvePoint_shouldReturnAddView_whenBindingResultHasErrors() {
      CurvePointDto curvePoint = new CurvePointDto();
      when(bindingResult.hasErrors()).thenReturn(true);

      String view = curvePointController.addCurvePoint(
        curvePoint,
        bindingResult,
        redirectAttributes,
        model
      );

      assertThat(view).isEqualTo("curvePoint/add");
      verifyNoInteractions(curvePointService);
    }

    @Test
    public void addCurvePoint_shouldAddCurvePoint_andReturnListView_whenNoErrors() {
      CurvePointDto curvePoint = new CurvePointDto();
      when(bindingResult.hasErrors()).thenReturn(false);

      String view = curvePointController.addCurvePoint(
        curvePoint,
        bindingResult,
        redirectAttributes,
        model
      );

      verify(curvePointService).addCurvePoint(any(CurvePointDto.class));
      assertThat(view).isEqualTo("redirect:/curvePoint/list");
    }

    @Test
    public void addCurvePoint_shouldHandleException_andRedirect() {
      CurvePointDto curvePoint = new CurvePointDto();
      when(bindingResult.hasErrors()).thenReturn(false);
      doThrow(new RuntimeException())
        .when(curvePointService)
        .addCurvePoint(any(CurvePointDto.class));

      String view = curvePointController.addCurvePoint(
        curvePoint,
        bindingResult,
        redirectAttributes,
        model
      );

      verify(curvePointService).addCurvePoint(any(CurvePointDto.class));
      assertThat(view).isEqualTo("redirect:/curvePoint/add");
    }
  }

  @Nested
  public class TestUpdateCurvePoint {

    @Test
    public void updateCurvePoint_shouldReturnUpdateView_whenBindingResultHasErrors() {
      CurvePointDto curvePoint = new CurvePointDto();
      when(bindingResult.hasErrors()).thenReturn(true);

      String view = curvePointController.updateCurvePoint(
        1,
        curvePoint,
        bindingResult,
        redirectAttributes,
        model
      );

      assertThat(view).isEqualTo("curvePoint/update");
      verifyNoInteractions(curvePointService);
    }

    @Test
    public void updateCurvePoint_shouldUpdateCurvePoint_andRedirect_whenNoErrors() {
      CurvePointDto curvePoint = new CurvePointDto();
      when(bindingResult.hasErrors()).thenReturn(false);

      String view = curvePointController.updateCurvePoint(
        1,
        curvePoint,
        bindingResult,
        redirectAttributes,
        model
      );

      verify(curvePointService).updateCurvePoint(
        eq(1),
        any(CurvePointDto.class)
      );
      assertThat(view).isEqualTo("redirect:/curvePoint/list");
    }

    @Test
    public void updateCurvePoint_shouldHandleException_andRedirect() {
      CurvePointDto curvePoint = new CurvePointDto();
      when(bindingResult.hasErrors()).thenReturn(false);
      doThrow(new RuntimeException())
        .when(curvePointService)
        .updateCurvePoint(eq(1), any(CurvePointDto.class));

      String view = curvePointController.updateCurvePoint(
        1,
        curvePoint,
        bindingResult,
        redirectAttributes,
        model
      );

      verify(curvePointService).updateCurvePoint(
        eq(1),
        any(CurvePointDto.class)
      );
      assertThat(view).isEqualTo("redirect:/curvePoint/list");
    }
  }
}
