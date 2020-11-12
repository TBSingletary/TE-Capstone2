package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

	private int transferId;
	private int transferTypeId;
	private int transferStatusId;
	private int userFromId;
	private int userToId;
	private int accountFrom;
	private int accountTo;
	private BigDecimal amount;
	private String userNameTo;
	private String userNameFrom;
	private int accountId;
	private String statusDescription;
	private String typeDescription;
	private String username;
	private String fromOrTo;
	
	public Transfer() {
		
	}
	
	public Transfer(int transferId, int transferTypeId, int transferStatusId, int userFromId, int userToId, int accountFrom, int accountTo, BigDecimal amount, String userNameTo, String userNameFrom, int accountId, String statusDescription, String typeDescription, String username, String fromOrTo) {
		this.transferId = transferId;
		this.transferTypeId = transferTypeId;
		this.transferStatusId = transferStatusId;
		this.userFromId = userFromId;
		this.userToId = userToId;
		this.accountFrom = accountFrom;
		this.accountTo = accountTo;
		this.amount = amount;
		this.userNameTo = userNameTo;
		this.userNameFrom = userNameFrom;
		this.accountId = accountId;
		this.statusDescription = statusDescription;
		this.typeDescription = typeDescription;
		this.username = username;
		this.fromOrTo = fromOrTo;
	}

	public int getTransferId() {
		return transferId;
	}

	public void setTransferId(int transferId) {
		this.transferId = transferId;
	}

	public int getTransferTypeId() {
		return transferTypeId;
	}

	public void setTransferTypeId(int transferTypeId) {
		this.transferTypeId = transferTypeId;
	}

	public int getTransferStatusId() {
		return transferStatusId;
	}

	public void setTransferStatusId(int transferStatusId) {
		this.transferStatusId = transferStatusId;
	}

	public int getUserFromId() {
		return userFromId;
	}

	public void setUserFromId(int userFromId) {
		this.userFromId = userFromId;
	}

	public int getUserToId() {
		return userToId;
	}

	public void setUserToId(int userToId) {
		this.userToId = userToId;
	}

	public int getAccountFrom() {
		return accountFrom;
	}

	public void setAccountFrom(int accountFrom) {
		this.accountFrom = accountFrom;
	}

	public int getAccountTo() {
		return accountTo;
	}

	public void setAccountTo(int accountTo) {
		this.accountTo = accountTo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getUserNameTo() {
		return userNameTo;
	}

	public void setUserNameTo(String userNameTo) {
		this.userNameTo = userNameTo;
	}

	public String getUserNameFrom() {
		return userNameFrom;
	}

	public void setUserNameFrom(String userNameFrom) {
		this.userNameFrom = userNameFrom;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String getTypeDescription() {
		return typeDescription;
	}

	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFromOrTo() {
		return fromOrTo;
	}

	public void setFromOrTo(String fromOrTo) {
		this.fromOrTo = fromOrTo;
	}

	
		
}
