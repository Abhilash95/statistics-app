package com.n26.statistic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.n26.statistic.dao.TransactionRepository;
import com.n26.statistic.dto.TransactionRequest;
import com.n26.statistic.dto.TransactionResponse;
import com.n26.statistic.model.TransactionBuilder;
import com.n26.statistic.service.TransactionService;
import com.n26.statistic.util.TransactionUtil;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository repository;

	@Override
	public TransactionResponse saveTransaction(TransactionRequest request) {

		TransactionResponse response = new TransactionResponse();

		if (TransactionUtil.isValidTransaction(request.getTimestamp())) {
			repository.save(TransactionBuilder.builder()
					.from(request)
					.build());
			response.setSucessTransaction(true);
		} else {
			response.setSucessTransaction(false);
		}

		return response;
	}

}
