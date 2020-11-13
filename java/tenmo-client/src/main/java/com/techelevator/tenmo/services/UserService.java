package com.techelevator.tenmo.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.User;

public class UserService {

	private final String BASE_URL;
	public RestTemplate restTemplate = new RestTemplate();

	public UserService(String url) {
		BASE_URL = url;
	}

	public User[] getAll(String token) {
		User[] user = null;
		user = restTemplate.exchange(BASE_URL + "users", HttpMethod.GET, makeAuthEntity(token), User[].class).getBody();
		return user;
	}

	private HttpEntity makeAuthEntity(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}
}