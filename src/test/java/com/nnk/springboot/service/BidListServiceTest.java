package com.nnk.springboot.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDto;
import com.nnk.springboot.repository.BidListRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BidListServiceTest {

  @Mock
  private BidListRepository bidListRepository;

  @Mock
  private BidListMapper bidListMapper;

  @InjectMocks
  private BidListService bidListService;

  @Nested
  public class TestGetAllBids {

    @Test
    public void getAllBids_shouldReturnAllBids() {
      List<BidList> bids = Arrays.asList(new BidList(), new BidList());
      when(bidListRepository.findAll()).thenReturn(bids);

      List<BidList> result = bidListService.getAllBids();

      assertThat(result).hasSize(2);
      verify(bidListRepository).findAll();
    }

    @Test
    public void getAllBids_shouldReturnEmptyList_whenNoBids() {
      when(bidListRepository.findAll()).thenReturn(Collections.emptyList());

      List<BidList> result = bidListService.getAllBids();

      assertThat(result).isEmpty();
      verify(bidListRepository).findAll();
    }
  }

  @Nested
  public class TestGetBidById {

    @Test
    public void getBidById_shouldReturnBid_whenIdExists() {
      BidList bid = new BidList();
      BidListDto bidDto = new BidListDto();
      when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));
      when(bidListMapper.bidListToBidListDto(bid)).thenReturn(bidDto);

      BidListDto result = bidListService.getBidById(1);

      assertThat(result).isNotNull();
      verify(bidListRepository).findById(1);
      verify(bidListMapper).bidListToBidListDto(bid);
    }

    @Test
    public void getBidById_shouldThrowException_whenIdDoesNotExist() {
      when(bidListRepository.findById(99)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> bidListService.getBidById(99))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Bid not found");
    }
  }

  @Nested
  public class TestAddBid {

    @Test
    public void addBid_shouldSaveBid() {
      BidListDto bidDto = new BidListDto();
      BidList entity = new BidList();

      when(bidListMapper.bidListDtoToBidList(bidDto)).thenReturn(entity);

      bidListService.addBid(bidDto);

      verify(bidListRepository).save(entity);
    }
  }

  @Nested
  public class TestUpdateBid {

    @Test
    public void updateBid_shouldUpdateAndSaveBid() {
      BidList existingBid = new BidList();
      BidListDto updatedBidDto = new BidListDto();

      when(bidListRepository.findById(1)).thenReturn(Optional.of(existingBid));
      when(bidListMapper.bidListDtoToBidList(1, updatedBidDto)).thenReturn(
        existingBid
      );

      bidListService.updateBid(1, updatedBidDto);

      verify(bidListRepository).findById(1);
      verify(bidListMapper).bidListDtoToBidList(1, updatedBidDto);
      verify(bidListRepository).save(existingBid);
    }

    @Test
    public void updateBid_shouldThrowException_whenBidNotFound() {
      BidListDto updatedBidDto = new BidListDto();
      when(bidListRepository.findById(2)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> bidListService.updateBid(2, updatedBidDto))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Bid not found");
    }
  }

  @Nested
  public class TestDeleteBid {

    @Test
    public void deleteBid_shouldDeleteBid() {
      BidList bid = new BidList();
      when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));

      bidListService.deleteBid(1);

      verify(bidListRepository).delete(bid);
    }

    @Test
    public void deleteBid_shouldThrowException_whenBidNotFound() {
      when(bidListRepository.findById(3)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> bidListService.deleteBid(3))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Bid not found");
    }
  }
}
