package com.techelevator.tenmo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
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

	@RequestMapping(path = "users/{username}/find", method = RequestMethod.GET)
	public User findByUsername(@PathVariable String username) {
		return userDAO.findByUsername(username);
	}

	@RequestMapping(path = "/accounts/{id}/users", method = RequestMethod.GET)
	public User findUserByAccountId(@PathVariable("id") int accountId) {
		return userDAO.findUserByAccountId(accountId);
	}

}
