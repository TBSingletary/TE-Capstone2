package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.List;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

public interface TransferDAO {

	List<User> getAllUsers();

	void addFunds(BigDecimal amount, int userToId);

	void withdrawFunds(BigDecimal amount, int userFromId);

	public Transfer addTransfer(Transfer transfer, BigDecimal amount, int toUserId, int fromUserId);

	public List <Transfer> getTransfers(int accountId);

	public List<Transfer> getTransferDetailsByTransferId(int transferId);

}
