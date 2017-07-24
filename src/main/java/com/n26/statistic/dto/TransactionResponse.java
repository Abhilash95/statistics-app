package com.n26.statistic.dto;

public class TransactionResponse {

	private boolean isSucessTransaction;

	public boolean isSucessTransaction() {
		return isSucessTransaction;
	}

	public void setSucessTransaction(boolean isSucessTransaction) {
		this.isSucessTransaction = isSucessTransaction;
	}

	@Override
	public String toString() {
		return "TransactionResponse [isSucessTransaction=" + isSucessTransaction + "]";
	}
	
}
