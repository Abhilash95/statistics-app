package com.n26.statistic.service;

import org.springframework.stereotype.Service;

import com.n26.statistic.dto.TransactionRequest;
import com.n26.statistic.dto.TransactionResponse;

/**
 * @author Abhilash
 *
 */
@Service
public interface TransactionService {

	TransactionResponse saveTransaction(TransactionRequest request);
}
