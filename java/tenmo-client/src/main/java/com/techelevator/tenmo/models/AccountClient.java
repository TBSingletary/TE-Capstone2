package com.techelevator.tenmo.models;

import java.math.BigDecimal;

public class AccountClient {

	private int accountId;
	private int userId;
	private BigDecimal balance;
	
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	@Override
	public String toString() {
		return "AccountClient [accountId=" + accountId + ", userId=" + userId + ", balance=" + balance + "]";
	}
	
	
}
