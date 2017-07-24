/**
 * 
 */
package com.n26.statistic.util;

import java.util.concurrent.TimeUnit;

/**
 * @author Abhilash
 *
 */
public class TransactionUtil {

	public static boolean isValidTransaction(String timestamp) {
		return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()-Long.parseLong(timestamp)) <= 60 ? true
				: false;
	}

}
