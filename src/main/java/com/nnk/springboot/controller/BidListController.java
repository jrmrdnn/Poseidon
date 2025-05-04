package com.nnk.springboot.controller;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDto;
import com.nnk.springboot.service.BidListService;
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
@RequestMapping("/bidList")
@Slf4j
@AllArgsConstructor
public class BidListController {

  private final BidListService bidListService;

  @GetMapping("/list")
  public String showBidList(
    RedirectAttributes redirectAttributes,
    Model model
  ) {
    try {
      List<BidList> bidList = bidListService.getAllBids();
      model.addAttribute("bidLists", bidList);
    } catch (Exception e) {
      log.error("Error fetching bid list: " + e.getMessage());
      model.addAttribute("errorMessage", "Error fetching bid list");
      model.addAttribute("bidLists", List.of());
    }

    return "bidList/list";
  }

  @GetMapping("/add")
  public String showAddBidForm(Model model) {
    BidListDto bidListDto = new BidListDto();
    model.addAttribute("bidListDto", bidListDto);
    return "bidList/add";
  }

  @GetMapping("/update/{id}")
  public String showUpdateBidForm(
    @PathVariable Integer id,
    RedirectAttributes redirectAttributes,
    Model model
  ) {
    try {
      BidListDto bidListDto = bidListService.getBidById(id);
      model.addAttribute("bidListDto", bidListDto);
      model.addAttribute("bidListId", id);
    } catch (Exception e) {
      log.error("Error fetching bid: " + e.getMessage());
      redirectAttributes.addFlashAttribute(
        "errorMessage",
        "Error fetching bid"
      );
      return "redirect:/bidList/list";
    }

    return "bidList/update";
  }

  @PostMapping
  public String addBid(
    @Valid BidListDto bidListDto,
    BindingResult result,
    RedirectAttributes redirectAttributes,
    Model model
  ) {
    if (result.hasErrors()) {
      model.addAttribute("bidListDto", bidListDto);
      return "bidList/add";
    }

    try {
      bidListService.addBid(bidListDto);
      redirectAttributes.addFlashAttribute(
        "successMessage",
        "Bid added successfully"
      );
    } catch (Exception e) {
      log.error("Error adding bid: " + e.getMessage());
      redirectAttributes.addFlashAttribute("errorMessage", "Error adding bid");
      return "redirect:/bidList/add";
    }

    return "redirect:/bidList/list";
  }

  @PutMapping("/{id}")
  public String updateBid(
    @PathVariable Integer id,
    @Valid BidListDto bidListDto,
    BindingResult result,
    RedirectAttributes redirectAttributes,
    Model model
  ) {
    if (result.hasErrors()) {
      model.addAttribute("bidListDto", bidListDto);
      model.addAttribute("bidListId", id);
      return "bidList/update";
    }

    try {
      bidListService.updateBid(id, bidListDto);
      redirectAttributes.addFlashAttribute(
        "successMessage",
        "Bid updated successfully"
      );
    } catch (Exception e) {
      log.error("Error updating bid: " + e.getMessage());
      model.addAttribute("errorMessage", "Error updating bidList");
      model.addAttribute("bidListDto", bidListDto);
      return "bidList/update";
    }

    return "redirect:/bidList/list";
  }

  @DeleteMapping("/{id}")
  public String deleteBid(
    @PathVariable Integer id,
    RedirectAttributes redirectAttributes,
    Model model
  ) {
    try {
      bidListService.deleteBid(id);
      redirectAttributes.addFlashAttribute(
        "successMessage",
        "Bid deleted successfully"
      );
    } catch (Exception e) {
      log.error("Error deleting bid: " + e.getMessage());
      model.addAttribute("errorMessage", "Error deleting bidList");
    }

    return "redirect:/bidList/list";
  }
}
