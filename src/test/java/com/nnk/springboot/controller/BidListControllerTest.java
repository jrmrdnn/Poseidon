package com.nnk.springboot.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDto;
import com.nnk.springboot.service.BidListService;
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
class BidListControllerTest {

  @Mock
  private BidListService bidListService;

  @Mock
  private Model model;

  @Mock
  private RedirectAttributes redirectAttributes;

  @Mock
  private BindingResult bindingResult;

  @InjectMocks
  private BidListController bidListController;

  @Nested
  public class TestShowBidList {

    @Test
    public void showBidList_shouldAddBidListToModel_andReturnListView() {
      BidList bid = new BidList();

      when(bidListService.getAllBids()).thenReturn(
        Collections.singletonList(bid)
      );

      String view = bidListController.showBidList(redirectAttributes, model);

      verify(model).addAttribute("bidLists", Collections.singletonList(bid));
      assertThat(view).isEqualTo("bidList/list");
    }

    @Test
    public void showBidList_shouldHandleException_andReturnListView() {
      when(bidListService.getAllBids()).thenThrow(new RuntimeException());

      String view = bidListController.showBidList(redirectAttributes, model);

      verify(model).addAttribute(eq("bidLists"), any());
      assertThat(view).isEqualTo("bidList/list");
    }
  }

  @Nested
  public class TestShowAddBidForm {

    @Test
    public void showAddBidForm_shouldAddNewBidListToModel_andReturnAddView() {
      String view = bidListController.showAddBidForm(model);

      verify(model).addAttribute(eq("bidListDto"), any(BidListDto.class));
      assertThat(view).isEqualTo("bidList/add");
    }
  }

  @Nested
  public class TestShowUpdateBidForm {

    @Test
    public void showUpdateBidForm_shouldAddBidListToModel_andReturnUpdateView() {
      BidListDto bid = new BidListDto();
      when(bidListService.getBidById(1)).thenReturn(bid);

      String view = bidListController.showUpdateBidForm(
        1,
        redirectAttributes,
        model
      );

      verify(model).addAttribute("bidListDto", bid);
      assertThat(view).isEqualTo("bidList/update");
    }

    @Test
    public void showUpdateBidForm_shouldHandleException_andReturnListView() {
      when(bidListService.getBidById(1)).thenThrow(new RuntimeException());

      String view = bidListController.showUpdateBidForm(
        1,
        redirectAttributes,
        model
      );

      assertThat(view).isEqualTo("redirect:/bidList/list");
    }
  }

  @Nested
  public class TestDeleteBid {

    @Test
    public void deleteBid_shouldDeleteBid_andRedirect() {
      String view = bidListController.deleteBid(1, redirectAttributes, model);

      verify(bidListService).deleteBid(1);
      assertThat(view).isEqualTo("redirect:/bidList/list");
    }

    @Test
    public void deleteBid_shouldHandleException_andReturnListView() {
      doThrow(new RuntimeException()).when(bidListService).deleteBid(1);

      String view = bidListController.deleteBid(1, redirectAttributes, model);

      assertThat(view).isEqualTo("redirect:/bidList/list");
    }
  }

  @Nested
  public class TestAddBid {

    @Test
    public void addBid_shouldReturnAddView_whenBindingResultHasErrors() {
      BidListDto bid = new BidListDto();
      when(bindingResult.hasErrors()).thenReturn(true);

      String view = bidListController.addBid(
        bid,
        bindingResult,
        redirectAttributes,
        model
      );

      assertThat(view).isEqualTo("bidList/add");
      verifyNoInteractions(bidListService);
    }

    @Test
    public void addBid_shouldAddBid_andReturnAddView_whenNoErrors() {
      BidListDto bid = new BidListDto();
      when(bindingResult.hasErrors()).thenReturn(false);

      String view = bidListController.addBid(
        bid,
        bindingResult,
        redirectAttributes,
        model
      );

      verify(bidListService).addBid(any(BidListDto.class));
      assertThat(view).isEqualTo("redirect:/bidList/list");
    }

    @Test
    public void addBid_shouldHandleException_andReturnAddView() {
      BidListDto bid = new BidListDto();
      when(bindingResult.hasErrors()).thenReturn(false);
      doThrow(new RuntimeException())
        .when(bidListService)
        .addBid(any(BidListDto.class));

      String view = bidListController.addBid(
        bid,
        bindingResult,
        redirectAttributes,
        model
      );

      assertThat(view).isEqualTo("redirect:/bidList/add");
    }
  }

  @Nested
  public class TestUpdateBid {

    @Test
    public void updateBid_shouldReturnUpdateView_whenBindingResultHasErrors() {
      BidListDto bid = new BidListDto();
      when(bindingResult.hasErrors()).thenReturn(true);

      String view = bidListController.updateBid(
        1,
        bid,
        bindingResult,
        redirectAttributes,
        model
      );

      assertThat(view).isEqualTo("bidList/update");
      verifyNoInteractions(bidListService);
    }

    @Test
    public void updateBid_shouldUpdateBid_andRedirect_whenNoErrors() {
      BidListDto bid = new BidListDto();
      when(bindingResult.hasErrors()).thenReturn(false);

      String view = bidListController.updateBid(
        1,
        bid,
        bindingResult,
        redirectAttributes,
        model
      );

      verify(bidListService).updateBid(eq(1), any(BidListDto.class));
      assertThat(view).isEqualTo("redirect:/bidList/list");
    }

    @Test
    public void updateBid_shouldHandleException_andReturnUpdateView() {
      BidListDto bid = new BidListDto();
      when(bindingResult.hasErrors()).thenReturn(false);
      doThrow(new RuntimeException())
        .when(bidListService)
        .updateBid(eq(1), any(BidListDto.class));

      String view = bidListController.updateBid(
        1,
        bid,
        bindingResult,
        redirectAttributes,
        model
      );

      verify(bidListService).updateBid(eq(1), any(BidListDto.class));
      assertThat(view).isEqualTo("bidList/update");
    }
  }
}
