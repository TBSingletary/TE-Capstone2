package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Transfer;

public interface TransferDAO {
	
	public Transfer createTransfer(Transfer transfer);
	
	public Transfer getTransferDetails(int transferId);
	
	public void updateTransferRequest(Transfer transfer, int id);
	
	public String getTransferStatus(Transfer transfer, int id);

	public List<Transfer> getAllTransfers(int id);

	Transfer getTransferFromDetails(int transferId);

}
