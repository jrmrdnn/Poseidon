package com.nnk.springboot.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.repository.TradeRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {

  @Mock
  private TradeRepository tradeRepository;

  @Mock
  private TradeMapper tradeMapper;

  @InjectMocks
  private TradeService tradeService;

  @Nested
  public class TestGetAllTrade {

    @Test
    public void getAllTrades_shouldReturnAllTrades() {
      List<Trade> trades = Arrays.asList(new Trade(), new Trade());
      when(tradeRepository.findAll()).thenReturn(trades);

      List<Trade> result = tradeService.getAllTrades();

      assertThat(result).hasSize(2);
      verify(tradeRepository).findAll();
    }

    @Test
    public void getAllTrades_shouldReturnEmptyList_whenNoTrades() {
      when(tradeRepository.findAll()).thenReturn(Collections.emptyList());

      List<Trade> result = tradeService.getAllTrades();

      assertThat(result).isEmpty();
      verify(tradeRepository).findAll();
    }
  }

  @Nested
  public class TestGetTradeById {

    @Test
    public void getTradeById_shouldReturnTrade_whenIdExists() {
      Trade trade = new Trade();
      TradeDto tradeDto = new TradeDto();

      when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
      when(tradeMapper.tradeToTradeDto(trade)).thenReturn(tradeDto);

      TradeDto result = tradeService.getTradeById(1);

      assertThat(result).isEqualTo(tradeDto);
      verify(tradeRepository).findById(1);
      verify(tradeMapper).tradeToTradeDto(trade);
    }

    @Test
    public void getTradeById_shouldThrowException_whenIdDoesNotExist() {
      when(tradeRepository.findById(99)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> tradeService.getTradeById(99))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Trade not found");
    }
  }

  @Nested
  public class TestAddTrade {

    @Test
    public void addTrade_shouldSaveTrade() {
      TradeDto tradeDto = new TradeDto();
      Trade trade = new Trade();

      when(tradeMapper.tradeDtoToTrade(tradeDto)).thenReturn(trade);

      tradeService.addTrade(tradeDto);

      verify(tradeMapper).tradeDtoToTrade(tradeDto);
      verify(tradeRepository).save(trade);
    }
  }

  @Nested
  public class TestUpdateTrade {

    @Test
    public void updateTrade_shouldUpdateTrade() {
      Trade existingTrade = new Trade();
      TradeDto updatedTradeDto = new TradeDto();

      when(tradeRepository.findById(1)).thenReturn(Optional.of(existingTrade));
      when(tradeMapper.tradeDtoToTrade(1, updatedTradeDto)).thenReturn(
        existingTrade
      );

      tradeService.updateTrade(1, updatedTradeDto);

      verify(tradeRepository).findById(1);
      verify(tradeMapper).tradeDtoToTrade(1, updatedTradeDto);
      verify(tradeRepository).save(existingTrade);
    }

    @Test
    public void updateTrade_shouldThrowException_whenTradeNotFound() {
      when(tradeRepository.findById(99)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> tradeService.updateTrade(99, new TradeDto()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Trade not found");
    }
  }

  @Nested
  public class TestDeleteTrade {

    @Test
    public void deleteTrade_shouldDeleteTrade() {
      Trade trade = new Trade();
      when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

      tradeService.deleteTrade(1);

      verify(tradeRepository).findById(1);
      verify(tradeRepository).delete(trade);
    }

    @Test
    public void deleteTrade_shouldThrowException_whenTradeNotFound() {
      when(tradeRepository.findById(99)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> tradeService.deleteTrade(99))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Trade not found");
    }
  }
}
