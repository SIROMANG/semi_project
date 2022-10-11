package com.greedy.semi.trade.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greedy.semi.trade.dto.TradeDTO;
import com.greedy.semi.trade.dto.TradeReplyDTO;
import com.greedy.semi.trade.entity.Trade;
import com.greedy.semi.trade.entity.TradeReply;
import com.greedy.semi.trade.repository.TradeReplyRepository;
import com.greedy.semi.trade.repository.TradeRepository;

@Service
@Transactional
public class TradeService {
	
	public static final int TRADE_PAGE_SIZE = 6;
	public static final String SORT_BY = "sellNo";
	public static final String SELL_DELETE = "N";
	
	private final TradeRepository tradeRepository;
	private final TradeReplyRepository tradeReplyRepository;
	private final ModelMapper modelMapper;
	
	public TradeService(TradeRepository tradeRepository, TradeReplyRepository tradeReplyRepository, ModelMapper modelMapper) {
		
		this.tradeRepository = tradeRepository;
		this.tradeReplyRepository = tradeReplyRepository;
		this.modelMapper = modelMapper;
		
	}
	
	public void registTrade(TradeDTO trade) {
		
		tradeRepository.save(modelMapper.map(trade, Trade.class));
		
	}

	public Page<TradeDTO> selectTradeList(int page, String searchValue) {
		
		Pageable pageable = PageRequest.of(page - 1, TRADE_PAGE_SIZE, Sort.by(SORT_BY).descending());
		
		Page<Trade> tradeList = tradeRepository.findBySearchValue(SELL_DELETE, searchValue, pageable);
		
		if(searchValue != null && !searchValue.isEmpty()) {
			
			tradeList = tradeRepository.findBySearchValue(SELL_DELETE, searchValue, pageable);
			
		} else {
			
			tradeList = tradeRepository.findBySellDelete(SELL_DELETE, pageable);
			
		}
		
		return tradeList.map(trade -> modelMapper.map(trade, TradeDTO.class));
		
	}

	public TradeDTO selectTradeDetail(Long sellNo) {
		
		Trade trade = tradeRepository.findBySellNoAndSellDelete(sellNo, SELL_DELETE);
		trade.setSellCount(trade.getSellCount() + 1);
		
		return modelMapper.map(trade, TradeDTO.class);
		
	}

	public void registTradeReply(TradeReplyDTO registTradeReply) {

		tradeReplyRepository.save(modelMapper.map(registTradeReply, TradeReply.class));
		
	}
	
	public List<TradeReplyDTO> loadTradeReply(TradeReplyDTO loadTradeReply) {
		
		List<TradeReply> tradeReplyList
			= tradeReplyRepository.findBySellNoAndReplyDelete(loadTradeReply.getSellNo(), SELL_DELETE);
		
		return tradeReplyList.stream().map(tradeReply -> modelMapper.map(tradeReply, TradeReplyDTO.class)).collect(Collectors.toList());
		
	}
	
	public void removeTradeReply(TradeReplyDTO removeTradeReply) {
		
		TradeReply foundTradeReply = tradeReplyRepository.findByReplyNo(removeTradeReply.getReplyNo());
		foundTradeReply.setReplyDelete('Y');
		
	}


}