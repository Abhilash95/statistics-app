package com.n26.statistic.model;

import com.n26.statistic.dto.TransactionRequest;

/**
 * @author Abhilash
 *
 * This is the builder class for Transaction Model class
 */
public class TransactionBuilder {

	private String timestamp;
	private Double amount;

	private TransactionBuilder() {
	}

	public TransactionBuilder timestamp(String timestamp) {
		this.timestamp = timestamp;
		return this;
	}

	public TransactionBuilder amount(Double amount) {
		this.amount = amount;
		return this;
	}

	public TransactionBuilder from(TransactionRequest request){
		return this.timestamp(request.getTimestamp())
		.amount(request.getAmount());
	}
	
	public Transaction build(){
		Transaction instance=new Transaction();
		instance.setAmount(this.amount);
		instance.setTimestamp(this.timestamp);
		return instance;
	}
	
	public static TransactionBuilder builder(){
		return new TransactionBuilder();
	}
}
