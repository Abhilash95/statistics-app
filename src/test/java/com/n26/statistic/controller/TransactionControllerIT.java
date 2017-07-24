package com.n26.statistic.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.statistic.StatisticsApplicationTests;
import com.n26.statistic.dao.TransactionRepository;
import com.n26.statistic.dto.TransactionRequest;
import com.n26.statistic.validator.TransactionRequestValidator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StatisticsApplicationTests.class)
public class TransactionControllerIT {
	
	@Autowired
	private TransactionController sut;
	
	@Autowired
	private TransactionRepository repository;

	private MockMvc mockMvc = null;
	private String endpoint = null;
	private ObjectMapper mapper = null;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(sut).setValidator(new TransactionRequestValidator()).build();
		endpoint = "/transactions";
		mapper = new ObjectMapper();
	}
	
	@After
	public void drillDown() {
		repository.deleteAll();
	}

	@Test
	public void shouldBeSuccessfulTransaction() throws JsonProcessingException, Exception {
		TransactionRequest request = new TransactionRequest();
		request.setAmount(10.0);
		request.setTimestamp(String.valueOf(System.currentTimeMillis() - 10000));
		
		ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)));

		perform.andExpect(status().isCreated());
	}
	
	@Test
	public void shouldBeUnSuccessfulTransaction() throws JsonProcessingException, Exception {
		TransactionRequest request = new TransactionRequest();
		request.setAmount(10.0);
		request.setTimestamp(String.valueOf(System.currentTimeMillis() - 80000));
		
		ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)));

		perform.andExpect(status().isNoContent());
	}
	
	@Test
	public void shouldBeUnSuccessfulAsAmountIsMissing() throws JsonProcessingException, Exception {
		TransactionRequest request = new TransactionRequest();
		request.setTimestamp(String.valueOf(System.currentTimeMillis() - 10000));
		
		ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)));

		perform.andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldBeUnSuccessfulAsAmountIsZero() throws JsonProcessingException, Exception {
		TransactionRequest request = new TransactionRequest();
		request.setAmount(0.0);
		request.setTimestamp(String.valueOf(System.currentTimeMillis() - 10000));
		
		ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)));

		perform.andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldBeUnSuccessfulAsTimeStampIsMissing() throws JsonProcessingException, Exception {
		TransactionRequest request = new TransactionRequest();
		request.setAmount(10.0);
		
		ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)));

		perform.andExpect(status().isBadRequest());
	}

}
