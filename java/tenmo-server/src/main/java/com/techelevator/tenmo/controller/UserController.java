package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.User;

@PreAuthorize("isAuthenticated()")
@RestController

public class UserController {

	private UserDAO userDAO;

	public UserController(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@RequestMapping(path = "users", method = RequestMethod.GET)
	public List<User> listAllUsers() {
		return userDAO.findAll();
	}

	@RequestMapping(path = "accounts/balance", method = RequestMethod.GET)
	public BigDecimal getUserBalance(Principal principal)
	{
		return userDAO.getUserBalance(principal.getName());
	}

}
