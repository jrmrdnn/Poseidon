package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BidListMapper {
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "account", source = "bidListDto.account")
  @Mapping(target = "type", source = "bidListDto.type")
  @Mapping(target = "bidQuantity", source = "bidListDto.quantity")
  BidList bidListDtoToBidList(BidListDto bidListDto);

  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "bidListId", source = "id")
  @Mapping(target = "account", source = "bidListDto.account")
  @Mapping(target = "type", source = "bidListDto.type")
  @Mapping(target = "bidQuantity", source = "bidListDto.quantity")
  BidList bidListDtoToBidList(Integer id, BidListDto bidListDto);

  @Mapping(target = "quantity", source = "bidList.bidQuantity")
  BidListDto bidListToBidListDto(BidList bidList);
}
