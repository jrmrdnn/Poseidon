package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.repository.TradeRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TradeService {

  private final TradeRepository tradeRepository;
  private final TradeMapper tradeMapper;

  /**
   * Get all trades
   * @return List of all trades
   */
  public List<Trade> getAllTrades() {
    return tradeRepository.findAll();
  }

  /**
   * Get a trade by its ID
   * @param id the ID of the trade
   * @return the trade with the specified ID
   */
  public TradeDto getTradeById(Integer id) {
    Trade trade = tradeRepository
      .findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Trade not found"));
    return tradeMapper.tradeToTradeDto(trade);
  }

  /**
   * Add a new trade
   * @param tradeDto the trade data
   */
  @Transactional
  public void addTrade(TradeDto tradeDto) {
    Trade trade = tradeMapper.tradeDtoToTrade(tradeDto);
    tradeRepository.save(trade);
  }

  /**
   * Update a trade
   * @param id the ID of the trade to update
   * @param tradeDto the updated trade data
   */
  @Transactional
  public void updateTrade(Integer id, TradeDto tradeDto) {
    Trade tradeRepo = tradeRepository
      .findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Trade not found"));
    Trade trade = tradeMapper.tradeDtoToTrade(id, tradeDto);
    trade.setCreationDate(tradeRepo.getCreationDate());
    tradeRepository.save(trade);
  }

  /**
   * Delete a trade
   * @param id the ID of the trade to delete
   */
  @Transactional
  public void deleteTrade(Integer id) {
    Trade trade = tradeRepository
      .findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Trade not found"));
    tradeRepository.delete(trade);
  }
}
