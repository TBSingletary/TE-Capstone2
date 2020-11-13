package com.techelevator.tenmo.services;

import java.math.BigDecimal;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class AccountService {

	private final String BASE_URL;
	public RestTemplate restTemplate = new RestTemplate();  

	public AccountService(String url) {
		BASE_URL = url;
	}

	public BigDecimal getAccountBalance(String token) {
		BigDecimal bigDecimal = null;
		bigDecimal = restTemplate.exchange(BASE_URL + "account/balance", HttpMethod.GET,makeAuthEntity(token),BigDecimal.class).getBody();
		return bigDecimal;
	}

	public int getAccountNumber(String token, int userId) {
		int accountNumber = restTemplate.exchange(BASE_URL + "account/number", HttpMethod.GET, makeAuthEntity(token), int.class).getBody();
		return accountNumber;
	}

	//PASSES TOKEN IN TO CONFIRM USER IS LOGGED IN AND CORRECT USER
	private HttpEntity makeAuthEntity(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}
}