package com.nnk.springboot.controller;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDto;
import com.nnk.springboot.service.RatingService;

@ExtendWith(MockitoExtension.class)
class RatingControllerTest {

  @Mock
  private RatingService ratingService;

  @Mock
  private Model model;

  @Mock
  private RedirectAttributes redirectAttributes;

  @Mock
  private BindingResult bindingResult;

  @InjectMocks
  private RatingController ratingController;

  @Nested
  public class TestShowRating {

    @Test
    public void showRating_shouldAddRatingsToModel_andReturnListView() {
      Rating rating = new Rating();
      when(ratingService.getAllRatings()).thenReturn(
        Collections.singletonList(rating)
      );

      String view = ratingController.showRating(redirectAttributes, model);
      verify(model).addAttribute("ratings", Collections.singletonList(rating));
      assertThat(view).isEqualTo("rating/list");
    }

    @Test
    public void showRating_shouldHandleException_andReturnListView() {
      when(ratingService.getAllRatings()).thenThrow(new RuntimeException());

      String view = ratingController.showRating(redirectAttributes, model);

      assertThat(view).isEqualTo("rating/list");
    }
  }

  @Nested
  public class TestShowAddRatingForm {

    @Test
    public void showAddRatingForm_shouldAddRatingToModel_andReturnAddView() {
      String view = ratingController.showAddRatingForm(model);

      verify(model).addAttribute(eq("ratingDto"), any(RatingDto.class));
      assertThat(view).isEqualTo("rating/add");
    }
  }

  @Nested
  public class TestShowUpdateBidForm {

    @Test
    public void showUpdateBidForm_shouldAddRatingToModel_andReturnUpdateView() {
      RatingDto ratingDto = new RatingDto();
      when(ratingService.getRatingById(1)).thenReturn(ratingDto);

      String view = ratingController.showUpdateRatingForm(
        1,
        redirectAttributes,
        model
      );

      verify(model).addAttribute("ratingDto", ratingDto);
      assertThat(view).isEqualTo("rating/update");
    }

    @Test
    public void showUpdateBidForm_shouldHandleException_andReturnListView() {
      when(ratingService.getRatingById(1)).thenThrow(new RuntimeException());

      String view = ratingController.showUpdateRatingForm(
        1,
        redirectAttributes,
        model
      );

      assertThat(view).isEqualTo("redirect:/rating/list");
    }
  }

  @Nested
  public class TestDeleteRating {

    @Test
    public void deleteBid_shouldDeleteRating_andRedirect() {
      String view = ratingController.deleteRating(1, redirectAttributes, model);

      verify(ratingService).deleteRating(1);
      assertThat(view).isEqualTo("redirect:/rating/list");
    }

    @Test
    public void deleteBid_shouldHandleException_andRedirect() {
      doThrow(new RuntimeException()).when(ratingService).deleteRating(1);

      String view = ratingController.deleteRating(1, redirectAttributes, model);

      assertThat(view).isEqualTo("redirect:/rating/list");
    }
  }

  @Nested
  public class TestValidateRating {

    @Test
    public void validateBid_shouldReturnAddView_whenBindingResultHasErrors() {
      RatingDto rating = new RatingDto();
      when(bindingResult.hasErrors()).thenReturn(true);

      String view = ratingController.validateRating(
        rating,
        bindingResult,
        redirectAttributes,
        model
      );

      assertThat(view).isEqualTo("rating/add");
      verifyNoInteractions(ratingService);
    }

    @Test
    public void validateBid_shouldAddRating_andRedirect_whenNoErrors() {
      RatingDto rating = new RatingDto();
      when(bindingResult.hasErrors()).thenReturn(false);

      String view = ratingController.validateRating(
        rating,
        bindingResult,
        redirectAttributes,
        model
      );

      verify(ratingService).addRating(any(RatingDto.class));
      assertThat(view).isEqualTo("redirect:/rating/list");
    }

    @Test
    public void validateBid_shouldHandleException_andRedirect() {
      RatingDto rating = new RatingDto();
      when(bindingResult.hasErrors()).thenReturn(false);
      doThrow(new RuntimeException())
        .when(ratingService)
        .addRating(any(RatingDto.class));

      String view = ratingController.validateRating(
        rating,
        bindingResult,
        redirectAttributes,
        model
      );

      verify(ratingService).addRating(any(RatingDto.class));
      assertThat(view).isEqualTo("redirect:/rating/add");
    }
  }

  @Nested
  public class TestUpdateRating {

    @Test
    public void updateRating_shouldReturnUpdateView_whenBindingResultHasErrors() {
      RatingDto rating = new RatingDto();
      when(bindingResult.hasErrors()).thenReturn(true);

      String view = ratingController.updateRating(
        1,
        rating,
        bindingResult,
        redirectAttributes,
        model
      );

      assertThat(view).isEqualTo("rating/update");
      verifyNoInteractions(ratingService);
    }

    @Test
    public void updateRating_shouldUpdateRating_andRedirect_whenNoErrors() {
      RatingDto rating = new RatingDto();
      when(bindingResult.hasErrors()).thenReturn(false);

      String view = ratingController.updateRating(
        1,
        rating,
        bindingResult,
        redirectAttributes,
        model
      );

      verify(ratingService).updateRating(eq(1), any(RatingDto.class));
      assertThat(view).isEqualTo("redirect:/rating/list");
    }

    @Test
    public void updateRating_shouldHandleException_andRedirect() {
      RatingDto rating = new RatingDto();
      when(bindingResult.hasErrors()).thenReturn(false);
      doThrow(new RuntimeException())
        .when(ratingService)
        .updateRating(eq(1), any(RatingDto.class));

      String view = ratingController.updateRating(
        1,
        rating,
        bindingResult,
        redirectAttributes,
        model
      );

      verify(ratingService).updateRating(eq(1), any(RatingDto.class));
      assertThat(view).isEqualTo("redirect:/rating/list");
    }
  }
}
