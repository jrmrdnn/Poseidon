package com.nnk.springboot.controller;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.service.TradeService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@AllArgsConstructor
public class TradeController {

  private final TradeService tradeService;

  @GetMapping("/trade/list")
  public String showTrade(RedirectAttributes redirectAttributes, Model model) {
    try {
      List<Trade> trades = tradeService.getAllTrades();
      model.addAttribute("trades", trades);
    } catch (Exception e) {
      log.error("Error fetching trades: " + e.getMessage());
      redirectAttributes.addFlashAttribute(
        "errorMessage",
        "Error fetching trades"
      );
      model.addAttribute("trades", List.of());
    }

    return "trade/list";
  }

  @GetMapping("/trade/add")
  public String showAddTradeForm(Model model) {
    TradeDto tradeDto = new TradeDto();
    model.addAttribute("tradeDto", tradeDto);
    return "trade/add";
  }

  @GetMapping("/trade/update/{id}")
  public String showUpdateTradeForm(
    @PathVariable Integer id,
    RedirectAttributes redirectAttributes,
    Model model
  ) {
    try {
      TradeDto tradeDto = tradeService.getTradeById(id);
      model.addAttribute("tradeDto", tradeDto);
      model.addAttribute("tradeId", id);
    } catch (Exception e) {
      log.error("Error fetching trade: " + e.getMessage());
      redirectAttributes.addFlashAttribute(
        "errorMessage",
        "Error fetching trade"
      );
      return "redirect:/trade/list";
    }

    return "trade/update";
  }

  @PostMapping("/trade/validate")
  public String validateTrade(
    @Valid TradeDto tradeDto,
    BindingResult result,
    RedirectAttributes redirectAttributes,
    Model model
  ) {
    if (result.hasErrors()) {
      model.addAttribute("tradeDto", tradeDto);
      return "trade/add";
    }

    try {
      tradeService.addTrade(tradeDto);
      redirectAttributes.addFlashAttribute(
        "successMessage",
        "Trade added successfully"
      );
    } catch (Exception e) {
      log.error("Error adding trade: " + e.getMessage());
      redirectAttributes.addFlashAttribute(
        "errorMessage",
        "Error adding trade"
      );
      return "redirect:/trade/add";
    }

    return "redirect:/trade/list";
  }

  @PostMapping("/trade/update/{id}")
  public String updateTrade(
    @PathVariable Integer id,
    @Valid TradeDto tradeDto,
    BindingResult result,
    RedirectAttributes redirectAttributes,
    Model model
  ) {
    if (result.hasErrors()) {
      model.addAttribute("tradeDto", tradeDto);
      model.addAttribute("tradeId", id);
      return "trade/update";
    }

    try {
      tradeService.updateTrade(id, tradeDto);
      redirectAttributes.addFlashAttribute(
        "successMessage",
        "Trade updated successfully"
      );
    } catch (Exception e) {
      log.error("Error updating trade: " + e.getMessage());
      redirectAttributes.addFlashAttribute(
        "errorMessage",
        "Error updating trade"
      );
    }

    return "redirect:/trade/list";
  }

  @PostMapping("/trade/delete/{id}")
  public String deleteTrade(
    @PathVariable Integer id,
    RedirectAttributes redirectAttributes,
    Model model
  ) {
    try {
      tradeService.deleteTrade(id);
      redirectAttributes.addFlashAttribute(
        "successMessage",
        "Trade deleted successfully"
      );
    } catch (Exception e) {
      log.error("Error deleting trade: " + e.getMessage());
      redirectAttributes.addFlashAttribute(
        "errorMessage",
        "Error deleting trade"
      );
    }

    return "redirect:/trade/list";
  }
}
