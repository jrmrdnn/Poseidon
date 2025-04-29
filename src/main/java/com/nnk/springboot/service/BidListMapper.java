package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BidListMapper {
  @Mapping(target = "bidQuantity", source = "bidListDto.quantity")
  BidList bidListDtoToBidList(BidListDto bidListDto);

  @Mapping(target = "quantity", source = "bidList.bidQuantity")
  BidListDto bidListToBidListDto(BidList bidList);

  @Mapping(target = "bidListId", source = "id")
  @Mapping(target = "bidQuantity", source = "bidListDto.quantity")
  BidList bidListDtoToBidList(Integer id, BidListDto bidListDto);
}
