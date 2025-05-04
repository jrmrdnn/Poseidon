package com.nnk.springboot.controller;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.service.CurvePointService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/curvePoint")
@Slf4j
@AllArgsConstructor
public class CurvePointController {

  private final CurvePointService curvePointService;

  @GetMapping("/list")
  public String showCurvePoint(
    RedirectAttributes redirectAttributes,
    Model model
  ) {
    try {
      List<CurvePoint> curvePoints = curvePointService.getAllCurvePoints();
      model.addAttribute("curvePoints", curvePoints);
    } catch (Exception e) {
      log.error("Error fetching curve points: " + e.getMessage());
      model.addAttribute("errorMessage", "Error fetching curve points");
      model.addAttribute("curvePoints", List.of());
    }

    return "curvePoint/list";
  }

  @GetMapping("/add")
  public String showAddCurvePointForm(Model model) {
    CurvePointDto curvePointDto = new CurvePointDto();
    model.addAttribute("curvePointDto", curvePointDto);
    return "curvePoint/add";
  }

  @GetMapping("/update/{id}")
  public String showUpdateCurvePointForm(
    @PathVariable Integer id,
    RedirectAttributes redirectAttributes,
    Model model
  ) {
    try {
      CurvePointDto curvePointDto = curvePointService.getCurvePointById(id);
      model.addAttribute("curvePointDto", curvePointDto);
      model.addAttribute("curvePointId", id);
    } catch (Exception e) {
      log.error("Error fetching curve point: " + e.getMessage());
      redirectAttributes.addFlashAttribute(
        "errorMessage",
        "Error fetching curve point"
      );
      return "redirect:/curvePoint/list";
    }

    return "curvePoint/update";
  }

  @PostMapping
  public String addCurvePoint(
    @Valid CurvePointDto curvePointDto,
    BindingResult result,
    RedirectAttributes redirectAttributes,
    Model model
  ) {
    if (result.hasErrors()) {
      model.addAttribute("curvePointDto", curvePointDto);
      return "curvePoint/add";
    }

    try {
      curvePointService.addCurvePoint(curvePointDto);
      redirectAttributes.addFlashAttribute(
        "successMessage",
        "CurvePoint added successfully"
      );
    } catch (Exception e) {
      log.error("Error adding curve point: " + e.getMessage());
      redirectAttributes.addFlashAttribute(
        "errorMessage",
        "Error adding curve point"
      );
      return "redirect:/curvePoint/add";
    }

    return "redirect:/curvePoint/list";
  }

  @PutMapping("/{id}")
  public String updateCurvePoint(
    @PathVariable Integer id,
    @Valid CurvePointDto curvePointDto,
    BindingResult result,
    RedirectAttributes redirectAttributes,
    Model model
  ) {
    if (result.hasErrors()) {
      model.addAttribute("curvePointDto", curvePointDto);
      model.addAttribute("curvePointId", id);
      return "curvePoint/update";
    }

    try {
      curvePointService.updateCurvePoint(id, curvePointDto);
      redirectAttributes.addFlashAttribute(
        "successMessage",
        "CurvePoint updated successfully"
      );
    } catch (Exception e) {
      log.error("Error updating curve point: " + e.getMessage());
      redirectAttributes.addFlashAttribute(
        "errorMessage",
        "Error updating curve point"
      );
    }

    return "redirect:/curvePoint/list";
  }

  @DeleteMapping("/{id}")
  public String deleteCurvePoint(
    @PathVariable Integer id,
    RedirectAttributes redirectAttributes,
    Model model
  ) {
    try {
      curvePointService.deleteCurvePoint(id);
      redirectAttributes.addFlashAttribute(
        "successMessage",
        "CurvePoint deleted successfully"
      );
    } catch (Exception e) {
      log.error("Error deleting curve point: " + e.getMessage());
      redirectAttributes.addFlashAttribute(
        "errorMessage",
        "Error deleting curve point"
      );
    }

    return "redirect:/curvePoint/list";
  }
}
