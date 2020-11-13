package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;

@RestController
@RequestMapping("/account")
public class AccountController {

	private AccountDAO accountDAO;

	public AccountController(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	@RequestMapping(path = "/balance", method = RequestMethod.GET)
	public BigDecimal getAccountBalance(Principal principal) {
		return accountDAO.getAccountBalance(principal.getName());
	}

	@RequestMapping(path = "/number", method = RequestMethod.GET)
	public long getAccountNumber(long userId) {
		return accountDAO.getAccountNumber(userId);
	}

}