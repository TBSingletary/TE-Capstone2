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

@PreAuthorize("isAuthenticated()")
@RestController

public class TransferController {
	private TransferDAO transferDAO;

	public TransferController(TransferDAO transfersDAO) {
		this.transferDAO = transfersDAO;
	}	

	@RequestMapping(path = "transfers/new", method = RequestMethod.POST)
	public void createTransfer(@RequestBody Transfer transfer)
	{
		transferDAO.createTransfer(transfer);
	}

	@RequestMapping(path = "transfers/{id}/getDetails", method = RequestMethod.GET)
	public Transfer getTransferDetails(@PathVariable Long transferId) {
		return transferDAO.getTransferDetails(transferId);
	}

	@RequestMapping(path = "transfers/{id}", method = RequestMethod.PUT)
	public void updateTransferRequest(@RequestBody Transfer transfer, @PathVariable int id) {
		transferDAO.updateTransferRequest(transfer, id);
	}

	@RequestMapping(path = "transfers/transfer_status_desc/{id}", method = RequestMethod.GET)
	public String getTransferStatus(@RequestBody Transfer transfer, @PathVariable int id) {
		return transferDAO.getTransferStatus(transfer, id);
	}

	@RequestMapping(path = "transfers/{id}/getAll", method = RequestMethod.GET)
	public List<Transfer> getAllTransfers(@PathVariable int id) {
		return transferDAO.getAllTransfers(id);
	}


}
