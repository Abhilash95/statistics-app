package com.n26.statistic.service.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.n26.statistic.dao.TransactionRepository;
import com.n26.statistic.dto.StatsticsResponse;
import com.n26.statistic.model.Transaction;

@RunWith(MockitoJUnitRunner.class)
public class StatsticsServiceImplTest {

	@InjectMocks
	private StatsticsServiceImpl sut;

	@Mock
	private TransactionRepository repository;

	@Test
	public void shouldReturnStatsFromLast60Seconds() {
		doReturn(getTransactionList()).when(repository).findAll();

		StatsticsResponse statstics = sut.getStatstics();

		assertNotNull(statstics);
		assertThat(statstics.getSum(), is(60.0));
		assertThat(statstics.getAvg(), is(20.0));
		assertThat(statstics.getCount(), is(3L));
		assertThat(statstics.getMax(), is(30.0));
		assertThat(statstics.getMin(), is(10.0));
	}

	@Test
	public void shouldRemoveInValidTransactionAndReturnSuccess() {
		List<Transaction> transactions = getTransactionOlderThan60Seconds();
		transactions.addAll(getTransactionList());
		doReturn(transactions).when(repository).findAll();
		StatsticsResponse statstics = sut.getStatstics();

		assertNotNull(statstics);
		assertThat(statstics.getSum(), is(60.0));
		assertThat(statstics.getAvg(), is(20.0));
		assertThat(statstics.getCount(), is(3L));
		assertThat(statstics.getMax(), is(30.0));
		assertThat(statstics.getMin(), is(10.0));
	}

	private List<Transaction> getTransactionList() {
		List<Transaction> transactions = new ArrayList<>();
		Transaction transaction = new Transaction();
		transaction.setAmount(10.0);
		transaction.setTimestamp(String.valueOf(System.currentTimeMillis() - 1000));
		transactions.add(transaction);

		transaction = new Transaction();
		transaction.setAmount(20.0);
		transaction.setTimestamp(String.valueOf(System.currentTimeMillis() - 2000));
		transactions.add(transaction);

		transaction = new Transaction();
		transaction.setAmount(30.0);
		transaction.setTimestamp(String.valueOf(System.currentTimeMillis()));
		transactions.add(transaction);

		return transactions;
	}

	private List<Transaction> getTransactionOlderThan60Seconds() {
		List<Transaction> transactions = new ArrayList<>();
		Transaction transaction = new Transaction();
		transaction.setAmount(10.0);
		transaction.setTimestamp(String.valueOf(System.currentTimeMillis() - 70000));
		transactions.add(transaction);

		transaction = new Transaction();
		transaction.setAmount(20.0);
		transaction.setTimestamp(String.valueOf(System.currentTimeMillis() - 80000));
		transactions.add(transaction);

		return transactions;
	}

}
