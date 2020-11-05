package com.techelevator.tenmo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.TransfersDAO;
import com.techelevator.tenmo.model.User;

@PreAuthorize("isAuthenticated()")
@RestController
public class TransferController {
//	private TransfersDAO transfersDAO;
//	
//	public TransferController(TransfersDAO transfersDAO)
//	{
//		this.transfersDAO = transfersDAO;
//	}
//	
//	@RequestMapping(path = "/balance", method = RequestMethod.POST)
//	public double getUserBalance(@RequestBody User user)
//	{
//		return transfersDAO.getBalance(user.getUsername());
//	}
	
}
