package com.nnk.springboot.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.repository.CurvePointRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CurvePointService {

  private final CurvePointRepository curvePointRepository;
  private final CurvePointMapper curvePointMapper;

  /**
   * Get all curve points
   * @return List of all curve points
   */
  public List<CurvePoint> getAllCurvePoints() {
    return curvePointRepository.findAll();
  }

  /**
   * Get a curve point by its ID
   * @param id the ID of the curve point
   * @return the curve point with the specified ID
   */
  public CurvePointDto getCurvePointById(Integer id) {
    CurvePoint curvePoint = curvePointRepository
      .findById(id)
      .orElseThrow(() -> new IllegalArgumentException("CurvePoint not found")
      );
    return curvePointMapper.curvePointToCurvePointDto(curvePoint);
  }

  /**
   * Add a new curve point
   * @param curvePointDto the curve point data
   */
  @Transactional
  public void addCurvePoint(CurvePointDto curvePointDto) {
    CurvePoint curvePoint = curvePointMapper.curvePointDtoToCurvePoint(curvePointDto);
    curvePointRepository.save(curvePoint);
  }

  /**
   * Update a curve point
   * @param id the ID of the curve point to update
   * @param curvePointDto the updated curve point data
   */
  @Transactional
  public void updateCurvePoint(Integer id, CurvePointDto curvePointDto) {
    curvePointRepository
      .findById(id)
      .orElseThrow(() -> new IllegalArgumentException("CurvePoint not found"));
    CurvePoint curvePoint = curvePointMapper.curvePointDtoToCurvePoint(id, curvePointDto);
    curvePointRepository.save(curvePoint);
  }

  /**
   * Delete a curve point
   * @param id the ID of the curve point to delete
   */
  @Transactional
  public void deleteCurvePoint(Integer id) {
    CurvePoint curvePoint = curvePointRepository
      .findById(id)
      .orElseThrow(() -> new IllegalArgumentException("CurvePoint not found"));
    curvePointRepository.delete(curvePoint);
  }
}
