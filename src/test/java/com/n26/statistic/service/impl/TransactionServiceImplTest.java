package com.n26.statistic.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.n26.statistic.dao.TransactionRepository;
import com.n26.statistic.dto.TransactionRequest;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest {

	@InjectMocks
	private TransactionServiceImpl sut;
	
	@Mock
	private TransactionRepository repository;
	
	@Test
	public void shouldReturnTrueIfTransactionIsSuccess() {
		TransactionRequest request = new TransactionRequest();
		request.setAmount(10.0);
		request.setTimestamp(String.valueOf(System.currentTimeMillis() - 10000));
		assertTrue(sut.saveTransaction(request).isSucessTransaction());
	}

	@Test
	public void shouldReturnFalseIfTransactionIsSuccess() {
		TransactionRequest request = new TransactionRequest();
		request.setAmount(10.0);
		request.setTimestamp(String.valueOf(System.currentTimeMillis() - 70000));
		assertFalse(sut.saveTransaction(request).isSucessTransaction());
	}
}
