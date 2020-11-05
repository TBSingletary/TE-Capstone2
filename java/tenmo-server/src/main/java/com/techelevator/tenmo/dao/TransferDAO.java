package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Transfer;

public interface TransferDAO {
	
	public void transferFunds(Transfer transfer);
	
	public double getBalance(String username);
	
	public String getTransferDetails(int id);
	
	public void updateTransferRequest(Transfer transfer);
	
	public String getTransferStatus(Transfer transfer);

	public List<Transfer> getTransferHistory(Transfer transfer);

	public void createTransfer(Transfer transfer);
}
