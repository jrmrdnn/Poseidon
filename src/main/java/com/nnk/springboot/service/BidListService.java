package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDto;
import com.nnk.springboot.repository.BidListRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class BidListService {

  private final BidListRepository bidListRepository;
  private final BidListMapper bidListMapper;

  /**
   * Get all bids
   * @return List of all bids
   */
  public List<BidList> getAllBids() {
    return bidListRepository.findAll();
  }

  /**
   * Get a bid by its ID
   * @param id the ID of the bid
   * @return the bid with the specified ID
   */
  public BidListDto getBidById(Integer id) {
    BidList bidList = bidListRepository
      .findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Bid not found"));
    return bidListMapper.bidListToBidListDto(bidList);
  }

  /**
   * Add a new bid
   * @param bidListDto the bid data
   */
  @Transactional
  public void addBid(BidListDto bidListDto) {
    BidList bidList = bidListMapper.bidListDtoToBidList(bidListDto);
    bidListRepository.save(bidList);
  }

  /**
   * Update a bid
   * @param id the ID of the bid to update
   * @param bidListDto the updated bid data
   */
  @Transactional
  public void updateBid(Integer id, BidListDto bidListDto) {
    bidListRepository
      .findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Bid not found"));
    BidList bidList = bidListMapper.bidListDtoToBidList(id, bidListDto);
    bidListRepository.save(bidList);
  }

  /**
   * Delete a bid
   * @param id the ID of the bid to delete
   */
  @Transactional
  public void deleteBid(Integer id) {
    BidList bid = bidListRepository
      .findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Bid not found"));
    bidListRepository.delete(bid);
  }
}
