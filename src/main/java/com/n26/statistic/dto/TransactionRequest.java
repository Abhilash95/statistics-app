package com.n26.statistic.dto;

public class TransactionRequest {

	private double amount;
	private String timestamp;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "TransactionRequest [amount=" + amount + ", timestamp=" + timestamp + "]";
	}

}
