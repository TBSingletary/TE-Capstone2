package com.techelevator.tenmo.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@RequestMapping(path = "tranfers", method = RequestMethod.PUT)
	public void createTransfer(Transfer transfer)
	{
		transferDAO.createTransfer(transfer);
	}
	
	@RequestMapping(path = "transfers", method = RequestMethod.POST)
	public void transferFunds(@RequestBody Transfer transfer) {
		transferDAO.transferFunds(transfer);
	}	
	
	@RequestMapping(path = "balance", method = RequestMethod.GET)
	public double getUserBalance(@RequestBody User user)
	{
		return transferDAO.getUserBalance(user.getUsername());
	}
	
	@RequestMapping(path = "tranfers/{id}", method = RequestMethod.GET)
	public String getTransferDetails(@PathVariable int id)
	{
		return transferDAO.getTransferDetails(id);
	}
	
	@RequestMapping(path = "tranfers/{id}", method = RequestMethod.PUT)
	public void updateTransferRequest(@RequestBody Transfer transfer, @PathVariable int id)
	{
		transferDAO.updateTransferRequest(transfer, id);
	}

	@RequestMapping(path = "tranfers", method = RequestMethod.GET)
	public void getTransferStatus(@RequestBody Transfer transfer)
	{
		transferDAO.getTransferStatus(transfer);
	}
	
	@RequestMapping(path = "transfers", method = RequestMethod.GET)
	public List<Transfer> getAllTransfers(Transfer transfer)
	{
		return transferDAO.getAllTransfers(transfer);
	}
	
	
}
