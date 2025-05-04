package com.nnk.springboot.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.service.TradeService;
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
class TradeControllerTest {

  @Mock
  private TradeService tradeService;

  @Mock
  private Model model;

  @Mock
  private RedirectAttributes redirectAttributes;

  @Mock
  private BindingResult bindingResult;

  @InjectMocks
  private TradeController tradeController;

  @Nested
  public class TestShowTrade {

    @Test
    public void showTrade_shouldAddTradesToModel_andReturnListView() {
      Trade trade = new Trade();
      when(tradeService.getAllTrades()).thenReturn(
        Collections.singletonList(trade)
      );

      String view = tradeController.showTrade(redirectAttributes, model);
      verify(model).addAttribute("trades", Collections.singletonList(trade));
      assertThat(view).isEqualTo("trade/list");
    }

    @Test
    public void showTrade_shouldHandleException_andReturnListView() {
      when(tradeService.getAllTrades()).thenThrow(new RuntimeException());

      String view = tradeController.showTrade(redirectAttributes, model);

      assertThat(view).isEqualTo("trade/list");
    }
  }

  @Nested
  public class TestShowAddTradeForm {

    @Test
    public void showAddTradeForm_shouldReturnAddView() {
      String view = tradeController.showAddTradeForm(model);

      verify(model).addAttribute(eq("tradeDto"), any(TradeDto.class));
      assertThat(view).isEqualTo("trade/add");
    }
  }

  @Nested
  public class TestShowUpdateTradeForm {

    @Test
    public void showUpdateTradeForm_shouldAddTradeToModel_andReturnUpdateView() {
      TradeDto tradeDto = new TradeDto();
      when(tradeService.getTradeById(1)).thenReturn(tradeDto);

      String view = tradeController.showUpdateTradeForm(
        1,
        redirectAttributes,
        model
      );

      verify(model).addAttribute("tradeDto", tradeDto);
      assertThat(view).isEqualTo("trade/update");
    }

    @Test
    public void showUpdateTradeForm_shouldHandleException_andRedirect() {
      when(tradeService.getTradeById(1)).thenThrow(new RuntimeException());

      String view = tradeController.showUpdateTradeForm(
        1,
        redirectAttributes,
        model
      );

      assertThat(view).isEqualTo("redirect:/trade/list");
    }
  }

  @Nested
  public class TestDeleteTrade {

    @Test
    public void deleteTrade_shouldDeleteTrade_andRedirect() {
      String view = tradeController.deleteTrade(1, redirectAttributes, model);

      verify(tradeService).deleteTrade(1);
      assertThat(view).isEqualTo("redirect:/trade/list");
    }

    @Test
    public void deleteTrade_shouldHandleException_andRedirect() {
      doThrow(new RuntimeException()).when(tradeService).deleteTrade(1);

      String view = tradeController.deleteTrade(1, redirectAttributes, model);

      assertThat(view).isEqualTo("redirect:/trade/list");
    }
  }

  @Nested
  public class TestAddTrade {

    @Test
    public void addTrade_shouldReturnAddView_whenBindingResultHasErrors() {
      TradeDto trade = new TradeDto();
      when(bindingResult.hasErrors()).thenReturn(true);

      String view = tradeController.addTrade(
        trade,
        bindingResult,
        redirectAttributes,
        model
      );

      assertThat(view).isEqualTo("trade/add");
      verifyNoInteractions(tradeService);
    }

    @Test
    public void addTrade_shouldAddTrade_andRedirect_whenNoErrors() {
      TradeDto trade = new TradeDto();
      when(bindingResult.hasErrors()).thenReturn(false);

      String view = tradeController.addTrade(
        trade,
        bindingResult,
        redirectAttributes,
        model
      );

      verify(tradeService).addTrade(any(TradeDto.class));
      assertThat(view).isEqualTo("redirect:/trade/list");
    }

    @Test
    public void addTrade_shouldHandleException_andRedirect() {
      TradeDto trade = new TradeDto();
      when(bindingResult.hasErrors()).thenReturn(false);
      doThrow(new RuntimeException())
        .when(tradeService)
        .addTrade(any(TradeDto.class));

      String view = tradeController.addTrade(
        trade,
        bindingResult,
        redirectAttributes,
        model
      );

      verify(tradeService).addTrade(any(TradeDto.class));
      assertThat(view).isEqualTo("redirect:/trade/add");
    }
  }

  @Nested
  public class TestUpdateTrade {

    @Test
    public void updateTrade_shouldReturnUpdateView_whenBindingResultHasErrors() {
      TradeDto trade = new TradeDto();
      when(bindingResult.hasErrors()).thenReturn(true);

      String view = tradeController.updateTrade(
        1,
        trade,
        bindingResult,
        redirectAttributes,
        model
      );

      assertThat(view).isEqualTo("trade/update");
      verifyNoInteractions(tradeService);
    }

    @Test
    public void updateTrade_shouldUpdateTrade_andRedirect_whenNoErrors() {
      TradeDto trade = new TradeDto();
      when(bindingResult.hasErrors()).thenReturn(false);

      String view = tradeController.updateTrade(
        1,
        trade,
        bindingResult,
        redirectAttributes,
        model
      );

      verify(tradeService).updateTrade(eq(1), any(TradeDto.class));
      assertThat(view).isEqualTo("redirect:/trade/list");
    }

    @Test
    public void updateTrade_shouldHandleException_andRedirect() {
      TradeDto trade = new TradeDto();
      when(bindingResult.hasErrors()).thenReturn(false);
      doThrow(new RuntimeException())
        .when(tradeService)
        .updateTrade(eq(1), any(TradeDto.class));

      String view = tradeController.updateTrade(
        1,
        trade,
        bindingResult,
        redirectAttributes,
        model
      );

      verify(tradeService).updateTrade(eq(1), any(TradeDto.class));
      assertThat(view).isEqualTo("redirect:/trade/list");
    }
  }
}
