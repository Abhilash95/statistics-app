/**
 * 
 */
package com.n26.statistic.dto;

/**
 * @author Abhilash
 * 
 * This DTO objects returns SUM, AVG, MAX, MIN, COUNT.
 *
 */
public class StatsticsResponse {

	private double sum;
	private double avg;
	private double max;
	private double min;
	private long count;

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "StatsticsResponse [sum=" + sum + ", avg=" + avg + ", max=" + max + ", min=" + min + ", count=" + count
				+ "]";
	}

}
