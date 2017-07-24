package com.n26.statistic.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.n26.statistic.dto.TransactionRequest;
import com.n26.statistic.dto.TransactionResponse;
import com.n26.statistic.service.TransactionService;
import com.n26.statistic.validator.TransactionRequestValidator;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Abhilash
 *
 *	This controller class is used to save Transactions from last 60 seconds
 */
@RestController
public class TransactionController {
	
	@Autowired
	private TransactionRequestValidator validator;
	
	@Autowired
	private TransactionService service;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	
    @ApiOperation(value = "Save Statstic Transaction", httpMethod = "POST", consumes = "application/json")
    @ApiResponses(value = {
        @ApiResponse(
            code = 201,
            message = "Transaction saved successfully"
        ),
        @ApiResponse(
            code = 204,
            message = "Transaction was made 60 seconds before"
        )
    })
    @RequestMapping(value="/transactions", method = RequestMethod.POST)
    public ResponseEntity<Void> saveTransaction(@Valid @RequestBody TransactionRequest request) {

		TransactionResponse trasactionResponse = service.saveTransaction(request);
		ResponseEntity<Void> responseEntity = null;
		if (trasactionResponse.isSucessTransaction()) {
			responseEntity = new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return responseEntity;
    }

}
