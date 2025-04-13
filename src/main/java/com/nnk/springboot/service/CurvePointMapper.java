package com.nnk.springboot.service;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;

@Mapper(componentModel = "spring")
public interface CurvePointMapper {
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "curveId", source = "curvePointDto.curveId")
  @Mapping(target = "term", source = "curvePointDto.term")
  @Mapping(target = "value", source = "curvePointDto.value")
  CurvePoint curvePointDtoToCurvePoint(CurvePointDto curvePointDto);

  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "id", source = "id")
  @Mapping(target = "curveId", source = "curvePointDto.curveId")
  @Mapping(target = "term", source = "curvePointDto.term")
  @Mapping(target = "value", source = "curvePointDto.value")
  CurvePoint curvePointDtoToCurvePoint(Integer id, CurvePointDto curvePointDto);

  CurvePointDto curvePointToCurvePointDto(CurvePoint curvePoint);
}