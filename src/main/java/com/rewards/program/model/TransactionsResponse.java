package com.rewards.program.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that contains response information
 * 
 * @author Alexis
 *
 */
public class TransactionsResponse {

	private List<Transaction> transactions;

	public List<Transaction> getTransactions() {
		if (transactions == null) {
			transactions = new ArrayList<>();
		}
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

}
