package com.n26.statistic.controller;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.statistic.dto.TransactionRequest;
import com.n26.statistic.dto.TransactionResponse;
import com.n26.statistic.service.TransactionService;
import com.n26.statistic.validator.TransactionRequestValidator;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {

	@InjectMocks
	private TransactionController sut;

	@Mock
	private TransactionService service;

	private MockMvc mockMvc = null;
	private String endpoint = null;
	private ObjectMapper mapper = null;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(sut).setValidator(new TransactionRequestValidator()).build();
		endpoint = "/transactions";
		mapper = new ObjectMapper();
	}

	@Test
	public void shouldBeSuccessfulTransaction() throws JsonProcessingException, Exception {
		TransactionRequest request = new TransactionRequest();
		request.setAmount(10.0);
		request.setTimestamp(String.valueOf(System.currentTimeMillis() - 10000));

		TransactionResponse response = new TransactionResponse();
		response.setSucessTransaction(true);

		doReturn(response).when(service).saveTransaction(any(TransactionRequest.class));

		ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)));

		perform.andExpect(status().isCreated());
	}

	@Test
	public void shouldBeUnSuccessfulTransaction() throws JsonProcessingException, Exception {
		TransactionRequest request = new TransactionRequest();
		request.setAmount(10.0);
		request.setTimestamp(String.valueOf(System.currentTimeMillis() - 80000));

		TransactionResponse response = new TransactionResponse();
		response.setSucessTransaction(false);

		doReturn(response).when(service).saveTransaction(any(TransactionRequest.class));

		ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)));

		perform.andExpect(status().isNoContent());
	}

	@Test
	public void badRequestIfTimestampIsMissing() throws JsonProcessingException, Exception {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("amount", "10.0");

		ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(map)));

		perform.andExpect(status().isBadRequest());
	}
	
	@Test
	public void badRequestIfAmountIsMissing() throws JsonProcessingException, Exception {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("timestamp", "1212131232");

		ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(map)));

		perform.andExpect(status().isBadRequest());
	}
	
	@Test
	public void badRequestIfAmountIsNotPositive() throws JsonProcessingException, Exception {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("timestamp", "1212131232");
		map.add("amount", "0.0");
		
		ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(map)));

		perform.andExpect(status().isBadRequest());
	}

}
