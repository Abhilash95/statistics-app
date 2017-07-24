package com.n26.statistic.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.statistic.StatisticsApplicationTests;
import com.n26.statistic.dao.TransactionRepository;
import com.n26.statistic.dto.StatsticsResponse;
import com.n26.statistic.model.Transaction;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StatisticsApplicationTests.class)
public class StatsticsControllerIT {
	
	@Autowired
	private StatsticsController sut;
	
	@Autowired
	private TransactionRepository repository;
	
	private MockMvc mockMvc = null;
	private String endpoint = null;
	private ObjectMapper mapper = null;
	
	@Before
	public void setUp() throws IOException {
		mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
		endpoint = "/statistics";
		mapper = new ObjectMapper();
	}

	@After
	public void drillDown() {
		repository.deleteAll();
	}

	@Test
	public void shouldReturnAllStats() throws Exception {
		repository.save(getTransactionList());
		
		ResultActions perform = mockMvc
				.perform(MockMvcRequestBuilders.get(endpoint).contentType(MediaType.APPLICATION_JSON));

		perform.andExpect(status().isOk());
		StatsticsResponse response = mapper.readValue(perform.andReturn().getResponse().getContentAsString(),
				StatsticsResponse.class);
		assertThat(response.getSum(), is(60.0));
		assertThat(response.getAvg(), is(20.0));
		assertThat(response.getCount(), is(3L));
		assertThat(response.getMax(), is(30.0));
		assertThat(response.getMin(), is(10.0));
	}
	
	@Test
	public void shouldRemoveTransactionOlderThan60Seconds() throws Exception {
		List<Transaction> transactions = getTransactionOlderThan60Seconds();
		transactions.addAll(getTransactionList());
		repository.save(transactions);
		ResultActions perform = mockMvc
				.perform(MockMvcRequestBuilders.get(endpoint).contentType(MediaType.APPLICATION_JSON));
		perform.andExpect(status().isOk());
		StatsticsResponse response = mapper.readValue(perform.andReturn().getResponse().getContentAsString(),
				StatsticsResponse.class);
		
		assertThat(response.getSum(), is(60.0));
		assertThat(response.getAvg(), is(20.0));
		assertThat(response.getCount(), is(3L));
		assertThat(response.getMax(), is(30.0));
		assertThat(response.getMin(), is(10.0));
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
