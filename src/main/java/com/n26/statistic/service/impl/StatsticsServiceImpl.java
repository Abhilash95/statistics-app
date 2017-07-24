package com.n26.statistic.service.impl;

import java.util.DoubleSummaryStatistics;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.n26.statistic.dao.TransactionRepository;
import com.n26.statistic.dto.StatsticsResponse;
import com.n26.statistic.model.Transaction;
import com.n26.statistic.service.StatsticsService;
import com.n26.statistic.util.TransactionUtil;

/**
 * @author Abhilash
 *
 */
@Service
public class StatsticsServiceImpl implements StatsticsService {
	
	@Autowired
	private TransactionRepository repository;

	public StatsticsResponse getStatstics(){
		
		List<Transaction> transactionList = repository.findAll();
		
		 DoubleSummaryStatistics summaryStatistics = 
				 transactionList.parallelStream()
				 				.filter(t -> TransactionUtil.isValidTransaction(t.getTimestamp()))
				 				.mapToDouble(t->t.getAmount())
				 				.summaryStatistics();
				
		return getStatsticResponse(summaryStatistics);
		
	}

	protected StatsticsResponse getStatsticResponse(DoubleSummaryStatistics summaryStatistics) {
		StatsticsResponse response=new StatsticsResponse();
		response.setAvg(summaryStatistics.getAverage());
		response.setCount(summaryStatistics.getCount());
		response.setMax(summaryStatistics.getMax());
		response.setMin(summaryStatistics.getMin());
		response.setSum(summaryStatistics.getSum());
		return response;
	}
}
