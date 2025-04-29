package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CurvePointMapper {
  CurvePoint curvePointDtoToCurvePoint(CurvePointDto curvePointDto);

  CurvePointDto curvePointToCurvePointDto(CurvePoint curvePoint);

  CurvePoint curvePointDtoToCurvePoint(Integer id, CurvePointDto curvePointDto);
}
