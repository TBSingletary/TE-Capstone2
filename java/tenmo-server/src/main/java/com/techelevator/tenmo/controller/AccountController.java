package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.UserDAO;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/account")

public class AccountController {
	private AccountDAO accountDAO;
	private UserDAO userDAO;

	public AccountController(AccountDAO accountDAO, UserDAO userDAO) {
		this.accountDAO = accountDAO;
		this.userDAO = userDAO;
	}
	
	//gets balance form logged in user

	@RequestMapping(value = "/balance", method = RequestMethod.GET)
	public BigDecimal getBalance(Principal principal) throws UsernameNotFoundException {
		long userId = getCurrentUserId(principal);
		return accountDAO.getAccountByUserId(userId).getBalance();
	}

	public long getCurrentUserId(Principal principal) {
		return userDAO.findByUsername(principal.getName()).getId();
	}

}
