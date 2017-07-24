package com.n26.statistic.validator;

import org.springframework.stereotype.Component;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.n26.statistic.dto.TransactionRequest;
import com.n26.statistic.util.CustomValidationUtil;

/**
 * @author Abhilash
 *  
 *  This is validator class to validate the Transaction request
 */

@Component
public class TransactionRequestValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		 return clazz.isAssignableFrom(TransactionRequest.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		 CustomValidationUtil.rejectIfMissingOrWhitespace(errors, "timestamp");
		 CustomValidationUtil.validatePositiveDouble(errors, "amount");
	}
	

}
