package com.techelevator.tenmo.controller;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Transfer;

@RequestMapping("/account")
@PreAuthorize("isAuthenticated()")
@RestController
public class TransferController {

	private TransferDAO transferDAO;
	private UserDAO userDAO;
	private AccountController accountController;
	private Transfer transfer;

	public TransferController(TransferDAO transferDAO) {
		this.transferDAO = transferDAO;
	}

	@RequestMapping(path = "/transfer", method = RequestMethod.GET)
	public void getTransfers(Principal principal) {

		long userId = accountController.getCurrentUserId(principal);
		int userIdInt = (int) userId;
		transfer.setAccount_from(userIdInt);

	}

	@RequestMapping(value = "/transfer", method = RequestMethod.POST)
	public Transfer initiateNewTransfer(@RequestBody Transfer transfer) {
		Transfer pendingTransfer = null;
		pendingTransfer = transferDAO.initiateTransfer(transfer);
		transferDAO.updateBalances(pendingTransfer);
		return pendingTransfer;
	}

	// updates both accounts from the transfer
	@RequestMapping(value = "/transfer", method = RequestMethod.PUT)
	public void makeTransfer(@RequestBody Transfer transfer) {
		transferDAO.updateBalances(transfer);
	}


}
