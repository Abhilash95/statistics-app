package com.n26.statistic.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 
 * @author Abhilash
 * 
 * This is model class which is used to persist the Transaction object
 * in DB
 *
 */

@Entity
public class Transaction implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String timestamp;

	private Double amount;

	public Transaction() {
		super();
	}

	public Transaction(long id, String timestamp, Double amount) {
		super();
		this.id = id;
		this.timestamp = timestamp;
		this.amount = amount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", timestamp=" + timestamp + ", amount=" + amount + "]";
	}

}
