package com.n26.statistic.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.statistic.dto.StatsticsResponse;
import com.n26.statistic.service.StatsticsService;

@RunWith(MockitoJUnitRunner.class)
public class StatsticsControllerTest {

	@InjectMocks
	private StatsticsController sut;

	@Mock
	private StatsticsService service;

	private MockMvc mockMvc = null;
	private String endpoint = null;
	private ObjectMapper mapper = null;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
		endpoint = "/statistics";
		mapper = new ObjectMapper();
	}

	@Test
	public void shouldReturnAllStats() throws JsonProcessingException, Exception {

		StatsticsResponse mockedResponse = new StatsticsResponse();
		mockedResponse.setAvg(15.0);
		mockedResponse.setCount(2l);
		mockedResponse.setMax(20.0);
		mockedResponse.setMin(10.0);
		mockedResponse.setSum(30.0);

		doReturn(mockedResponse).when(service).getStatstics();

		ResultActions perform = mockMvc
				.perform(MockMvcRequestBuilders.get(endpoint).contentType(MediaType.APPLICATION_JSON));

		perform.andExpect(status().isOk());
		StatsticsResponse response = mapper.readValue(perform.andReturn().getResponse().getContentAsString(),
				StatsticsResponse.class);
		assertNotNull(response);
		assertThat(response.getSum(), is(30.0));
		assertThat(response.getAvg(), is(15.0));
		assertThat(response.getCount(), is(2L));
		assertThat(response.getMax(), is(20.0));
		assertThat(response.getMin(), is(10.0));
	}
	
	
}
