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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * TradeController handles requests related to Trade operations.
 */
@Controller
@RequestMapping("/trade")
@Slf4j
@AllArgsConstructor
public class TradeController {

  private final TradeService tradeService;

  /**
   * Shows the trade list page.
   * @param redirectAttributes attributes for redirecting
   * @param model the model to be used in the view
   * @return Trade list page
   */
  @GetMapping("/list")
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

  /**
   * Shows the add trade page.
   * @param model the model to be used in the view
   * @return Trade add page
   */
  @GetMapping("/add")
  public String showAddTradeForm(Model model) {
    TradeDto tradeDto = new TradeDto();
    model.addAttribute("tradeDto", tradeDto);
    return "trade/add";
  }

  /**
   * Shows the update trade page.
   * @param id the id of the trade to be updated
   * @param redirectAttributes attributes for redirecting
   * @param model the model to be used in the view
   * @return Trade update page
   */
  @GetMapping("/update/{id}")
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

  /**
   * Adds a new trade.
   * @param tradeDto the trade data transfer object
   * @param result the binding result for validation errors
   * @param redirectAttributes attributes for redirecting
   * @param model the model to be used in the view
   * @return Trade list page or add page in case of error
   */
  @PostMapping
  public String addTrade(
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

  /**
   * Updates an existing trade.
   * @param id the id of the trade to be updated
   * @param tradeDto the trade data transfer object
   * @param result the binding result for validation errors
   * @param redirectAttributes attributes for redirecting
   * @param model the model to be used in the view
   * @return Trade list page or update page in case of error
   */
  @PutMapping("/{id}")
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

  /**
   * Deletes a trade.
   * @param id the id of the trade to be deleted
   * @param redirectAttributes attributes for redirecting
   * @param model the model to be used in the view
   * @return Trade list page
   */
  @DeleteMapping("/{id}")
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
