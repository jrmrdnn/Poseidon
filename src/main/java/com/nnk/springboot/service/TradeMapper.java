package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TradeMapper {
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "account", source = "tradeDto.account")
  @Mapping(target = "type", source = "tradeDto.type")
  @Mapping(target = "buyQuantity", source = "tradeDto.quantity")
  Trade tradeDtoToTrade(TradeDto tradeDto);

  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "tradeId", source = "id")
  @Mapping(target = "account", source = "tradeDto.account")
  @Mapping(target = "type", source = "tradeDto.type")
  @Mapping(target = "buyQuantity", source = "tradeDto.quantity")
  Trade tradeDtoToTrade(Integer id, TradeDto tradeDto);

  @Mapping(target = "quantity", source = "trade.buyQuantity")
  TradeDto tradeToTradeDto(Trade trade);
}
