package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.List;

import com.techelevator.tenmo.model.Transfer;

public interface TransferDAO {

	Transfer createTransfer(Transfer transfer);

	void subtractFromBalance(BigDecimal transferAmount, long userId);

	void addToBalance(BigDecimal transferAmount, long userId);

	List<Transfer> getTransferHistory(long userId);

}