package com.n26.statistic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.n26.statistic.dto.StatsticsResponse;
import com.n26.statistic.service.StatsticsService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Abhilash 
 * 
 * This controller class is used to get Transactions Stats from last 60 seconds
 */

@RestController
public class StatsticsController {

	@Autowired
	private StatsticsService service;

	@ApiOperation(value = "Get Statstics of Transaction", httpMethod = "GET", produces = "application/json")
	@ApiResponses(value ={ 
					@ApiResponse(
					  code = 200,
					  message = "Get Statstics of Transaction") 
				})
	@RequestMapping(value = "/statistics", method = RequestMethod.GET)
	public ResponseEntity<StatsticsResponse> transactionStatstics() {
		return new ResponseEntity<>(service.getStatstics(), HttpStatus.OK);
	}

}
