package com.techelevator.tenmo.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.model.Transfer;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("transfer")
public class TransferController {

	//CREATES DAO FOR TRANSFER
	private TransferDAO transferDAO;

	public TransferController(TransferDAO transferDAO) {

		this.transferDAO = transferDAO;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Transfer createTransfer(@Valid @RequestBody Transfer transfer, Principal principal) {
		Transfer newTransfer = null;
		newTransfer = transferDAO.createTransfer(transfer);
		if (newTransfer != null) {
			transferDAO.addToBalance(transfer.getTransferAmount(), transfer.getToUserId());
			transferDAO.subtractFromBalance(transfer.getTransferAmount(), transfer.getFromUserId());
			return newTransfer;
		}
		return null;
	}

	@RequestMapping(path = "/history/{userId}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.FOUND)
	public List<Transfer> getTransferHistory(@PathVariable int userId) {
		return transferDAO.getTransferHistory(userId);
	}
}