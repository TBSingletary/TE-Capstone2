package com.techelevator.tenmo.models;

import java.math.BigDecimal;

public class Account {

	private int accountId;
	private BigDecimal accountBalance;
	private int userId;
		
	public BigDecimal getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}
	public int getAccountId() {
		return accountId;
	}
	public int getUserId() {
		return userId;
	}
	
	
}