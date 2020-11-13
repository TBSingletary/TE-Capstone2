package com.techelevator.tenmo.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Transfer;

public class TransferService {

	private final String BASE_URL;
	public RestTemplate restTemplate = new RestTemplate();
	public TransferService(String url) {
		BASE_URL = url;
	}

	public Transfer createTransfer(String token, Transfer transfer) {

		ResponseEntity<Transfer> response = restTemplate.exchange(BASE_URL + "transfer", HttpMethod.POST, makeAuthEntity(token,transfer), Transfer.class);
		return response.getBody();
	}

	public Transfer[] getTransferHistory(String token, int id) {
		Transfer[] response = null;
		response = restTemplate.exchange(BASE_URL + "transfer/history/" + id, HttpMethod.GET, makeAuthEntity(token), Transfer[].class).getBody();
		return response;
	}

	private HttpEntity<Transfer> makeAuthEntity(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Transfer> entity = new HttpEntity<>(headers);
		return entity;
	}

	private HttpEntity<Transfer> makeAuthEntity(String token, Transfer transfer) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
		return entity;
	}
}