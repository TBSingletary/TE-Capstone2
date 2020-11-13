package com.techelevator.tenmo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.User;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserDAO dao;

	public UserController(UserDAO userDAO) {
		this.dao = userDAO;
	}

	@RequestMapping(path = "", method = RequestMethod.GET)
	public List<User> listUsers(){
		return dao.findAll();
	}
}