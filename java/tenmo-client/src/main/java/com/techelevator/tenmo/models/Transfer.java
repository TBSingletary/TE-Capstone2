package com.techelevator.tenmo.models;

import java.math.BigDecimal;

public class Transfer {
	
	private long transferId;
	private long fromUserId;
	private long toUserId;
	private long typeOfTransferId;
	private long statusOfTransferId;
	private String statusOfTransfer;
	private String fromUsername;
	private String toUserName;
	private String typeOfTransfer;
	private BigDecimal transferAmount;	
	
	public BigDecimal getTransferAmount() {
		return transferAmount;
	}
	public void setTransferAmount(BigDecimal transferAmount) {
		this.transferAmount = transferAmount;
	}
	
	public long getToUserId() {
		return toUserId;
	}
	public void setToUserId(long toUserId) {
		this.toUserId = toUserId;
	}
	public long getStatusOfTransferId() {
		return statusOfTransferId;
	}
	public void setStatusOfTransferId(long statusOfTransferId) {
		this.statusOfTransferId = statusOfTransferId;
	}
	public long getTypeOfTransferId() {
		return typeOfTransferId;
	}
	public void setTypeOfTransferId(long typeOfTransferId) {
		this.typeOfTransferId = typeOfTransferId;
	}
	public long getTransferId() {
		return transferId;
	}
	public void setTransferId(long transferId) {
		this.transferId = transferId;
	}
	public long getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(long fromUserId) {
		this.fromUserId = fromUserId;
	}
	public String getTypeOfTransfer() {
		return typeOfTransfer;
	}
	public void setTypeOfTransfer(String typeOfTransfer) {
		this.typeOfTransfer = typeOfTransfer;
	}
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getFromUsername() {
		return fromUsername;
	}
	public void setFromUsername(String fromUsername) {
		this.fromUsername = fromUsername;
	}
	public String getStatusOfTransfer() {
		return statusOfTransfer;
	}
	public void setStatusOfTransfer(String statusOfTransfer) {
		this.statusOfTransfer = statusOfTransfer;
	}

}
	