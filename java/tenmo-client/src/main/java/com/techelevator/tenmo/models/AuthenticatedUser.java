package com.techelevator.tenmo.models;

public class AuthenticatedUser {
	
	private String token;
	private UserClient user;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public UserClient getUser() {
		return user;
	}
	public void setUser(UserClient user) {
		this.user = user;
	}
}
