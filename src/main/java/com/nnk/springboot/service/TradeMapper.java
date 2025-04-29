package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TradeMapper {
  @Mapping(target = "buyQuantity", source = "tradeDto.quantity")
  Trade tradeDtoToTrade(TradeDto tradeDto);

  @Mapping(target = "quantity", source = "trade.buyQuantity")
  TradeDto tradeToTradeDto(Trade trade);

  @Mapping(target = "tradeId", source = "id")
  @Mapping(target = "buyQuantity", source = "tradeDto.quantity")
  Trade tradeDtoToTrade(Integer id, TradeDto tradeDto);
}
