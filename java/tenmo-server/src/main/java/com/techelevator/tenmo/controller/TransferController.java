package com.techelevator.tenmo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

@PreAuthorize("isAuthenticated()")
@RestController
public class TransferController {
	private TransferDAO transferDAO;
	
	public TransferController(TransferDAO transfersDAO)
	{
		this.transferDAO = transfersDAO;
	}
	
	@RequestMapping(path = "balance", method = RequestMethod.GET)
	public double getUserBalance(@RequestBody User user)
	{
		return transferDAO.getBalance(user.getUsername());
	}
	
	@RequestMapping(path = "transfers", method = RequestMethod.POST)
	public void transferFunds(@RequestBody Transfer transfer) {
		transferDAO.transferFunds(transfer);
	}
	
}
