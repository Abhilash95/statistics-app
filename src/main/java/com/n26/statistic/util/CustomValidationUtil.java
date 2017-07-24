package com.n26.statistic.util;

import java.util.function.Supplier;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class CustomValidationUtil {

	public static void validatePositiveDouble(Errors errors, String fieldName) {
		if (errors.getFieldType(fieldName).isAssignableFrom(Double.class)
				|| errors.getFieldType(fieldName).getName().equals("double")) {
			validatePositiveNumber(() -> Double.parseDouble(errors.getFieldValue(fieldName).toString()) <= 0.0,
					fieldName, errors);
		}
	}

	private static void validatePositiveNumber(Supplier<Boolean> filter, String fieldName, Errors errors) {
		if (filter.get()) {
			errors.rejectValue(fieldName, "shouldBePositive");
		}
	}

	public static void rejectIfMissingOrWhitespace(Errors errors, String field) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, field, "missingField");
	}

}
