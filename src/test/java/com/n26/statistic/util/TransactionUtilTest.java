package com.n26.statistic.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransactionUtilTest {

	@Test
	public void shouldBeSuccessTransaction() {
		assertTrue(TransactionUtil.isValidTransaction(String.valueOf(System.currentTimeMillis())));
	}

	@Test
	public void shouldBeUnSuccessTransaction() {
		assertFalse(TransactionUtil.isValidTransaction(String.valueOf(System.currentTimeMillis() - 70000)));
	}

	@Test
	public void shouldBeSuccessTransactionTimeDiffIs60Seconds() {
		assertTrue(TransactionUtil.isValidTransaction(String.valueOf(System.currentTimeMillis() - 60000)));
	}

}
