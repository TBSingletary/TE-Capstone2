package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

public interface TransferDAO {
	
	public void transferFunds(Transfer transfer);
	
	public Transfer getTransferDetails(int id);
	
	public void updateTransferRequest(Transfer transfer, int id);
	
	public String getTransferStatus(Transfer transfer, int id);

	public List<Transfer> getAllTransfers(int id);

	public void createTransfer(Transfer transfer);
}
