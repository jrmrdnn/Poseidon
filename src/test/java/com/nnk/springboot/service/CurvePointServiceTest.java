package com.nnk.springboot.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.repository.CurvePointRepository;

@ExtendWith(MockitoExtension.class)
public class CurvePointServiceTest {

  @Mock
  private CurvePointRepository curvePointRepository;

  @Mock
  private CurvePointMapper curvePointMapper;

  @InjectMocks
  private CurvePointService curvePointService;

  @Nested
  public class TestGetAllBids {

    @Test
    public void getAllCurvePoint_shouldReturnAllCurvePoints() {
      List<CurvePoint> curvePoints = Arrays.asList(
        new CurvePoint(),
        new CurvePoint()
      );
      when(curvePointRepository.findAll()).thenReturn(curvePoints);

      List<CurvePoint> result = curvePointService.getAllCurvePoints();

      assertThat(result).hasSize(2);
      verify(curvePointRepository).findAll();
    }

    @Test
    public void getAllCurvePoint_shouldReturnEmptyList_whenNoCurvePoints() {
      when(curvePointRepository.findAll()).thenReturn(Collections.emptyList());

      List<CurvePoint> result = curvePointService.getAllCurvePoints();

      assertThat(result).isEmpty();
      verify(curvePointRepository).findAll();
    }
  }

  @Nested
  public class TestGetCurvePointById {

    @Test
    public void getCurvePointById_shouldReturnCurvePoint_whenIdExists() {
      CurvePoint curvePoint = new CurvePoint();
      CurvePointDto curvePointDto = new CurvePointDto();

      when(curvePointRepository.findById(1)).thenReturn(
        Optional.of(curvePoint)
      );
      when(curvePointMapper.curvePointToCurvePointDto(curvePoint)).thenReturn(
        curvePointDto
      );

      CurvePointDto result = curvePointService.getCurvePointById(1);

      assertThat(result).isEqualTo(curvePointDto);
      verify(curvePointRepository).findById(1);
      verify(curvePointMapper).curvePointToCurvePointDto(curvePoint);
    }

    @Test
    public void getCurvePointById_shouldThrowException_whenIdDoesNotExist() {
      when(curvePointRepository.findById(99)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> curvePointService.getCurvePointById(99))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("CurvePoint not found");
    }
  }

  @Nested
  public class TestAddCurvePoint {

    @Test
    public void addCurvePoint_shouldSaveCurvePoint() {
      CurvePointDto curvePointDto = new CurvePointDto();
      CurvePoint curvePoint = new CurvePoint();

      when(
        curvePointMapper.curvePointDtoToCurvePoint(curvePointDto)
      ).thenReturn(curvePoint);

      curvePointService.addCurvePoint(curvePointDto);

      verify(curvePointMapper).curvePointDtoToCurvePoint(curvePointDto);
      verify(curvePointRepository).save(curvePoint);
    }
  }

  @Nested
  public class TestUpdateCurvePoint {

    @Test
    public void updateCurvePoint_shouldUpdateAndSaveCurvePoint() {
      CurvePoint existingCurvePoint = new CurvePoint();
      CurvePointDto updatedCurvePointDto = new CurvePointDto();

      when(curvePointRepository.findById(1)).thenReturn(
        Optional.of(existingCurvePoint)
      );
      when(
        curvePointMapper.curvePointDtoToCurvePoint(1, updatedCurvePointDto)
      ).thenReturn(existingCurvePoint);

      curvePointService.updateCurvePoint(1, updatedCurvePointDto);

      verify(curvePointRepository).findById(1);
      verify(curvePointMapper).curvePointDtoToCurvePoint(
        1,
        updatedCurvePointDto
      );
      verify(curvePointRepository).save(existingCurvePoint);
    }

    @Test
    public void updateCurvePoint_shouldThrowException_whenCurvePointNotFound() {
      CurvePointDto updatedCurvePointDto = new CurvePointDto();
      when(curvePointRepository.findById(2)).thenReturn(Optional.empty());

      assertThatThrownBy(() ->
        curvePointService.updateCurvePoint(2, updatedCurvePointDto)
      )
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("CurvePoint not found");
    }
  }

  @Nested
  public class TestDeleteCurvePoint {

    @Test
    public void deleteCurvePoint_shouldDeleteCurvePoint() {
      CurvePoint curvePoint = new CurvePoint();
      when(curvePointRepository.findById(1)).thenReturn(
        Optional.of(curvePoint)
      );

      curvePointService.deleteCurvePoint(1);

      verify(curvePointRepository).delete(curvePoint);
    }

    @Test
    public void deleteCurvePoint_shouldThrowException_whenCurvePointNotFound() {
      when(curvePointRepository.findById(3)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> curvePointService.deleteCurvePoint(3))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("CurvePoint not found");
    }
  }
}
