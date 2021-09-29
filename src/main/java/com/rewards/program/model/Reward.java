package com.rewards.program.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Reward {

	private Long custId;
	private String custName;
	@JsonInclude(Include.NON_NULL) 
	private String month;
	private int transactions;
	private Long totalPoints;
	
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getTransactions() {
		return transactions;
	}
	public void setTransactions(int transactions) {
		this.transactions = transactions;
	}
	public Long getTotalPoints() {
		return totalPoints;
	}
	public void setTotalPoints(Long totalPoints) {
		this.totalPoints = totalPoints;
	}

	

}
