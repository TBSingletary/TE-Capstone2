package com.techelevator.tenmo.services;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.TransferClient;
import com.techelevator.tenmo.models.UserClient;
import com.techelevator.view.ConsoleService;

public class TransferService {

	private String API_BASE_URL;
	private String jwt;

	private final RestTemplate restTemplate = new RestTemplate();
	private ConsoleService console;

	public TransferService(String jwt, String API_BASE_URL, ConsoleService console) {
		this.jwt = jwt;
		this.API_BASE_URL = API_BASE_URL;
		this.console = console;
	}

	public List<UserClient> getAllUsers() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwt);
		HttpEntity request = new HttpEntity<>(headers);
		List<UserClient> users = null;
		try {
			UserClient[] userArray = restTemplate.exchange(API_BASE_URL + "/users", HttpMethod.GET, request, UserClient[].class).getBody();
			users = Arrays.asList(userArray);
		} catch (RestClientResponseException ex) {
			console.printError(ex.getRawStatusCode() + " : " + ex.getStatusText());
		} catch (ResourceAccessException ex) {
			console.printError(ex.getMessage());
		}

		return users;
	}

	public TransferClient updateBalance(TransferClient transfer) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwt);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity request = new HttpEntity<>(transfer, headers);
		try {
			restTemplate.put(API_BASE_URL + "/accounts", request);
		} catch (RestClientResponseException ex) {
			console.printError(ex.getRawStatusCode() + " : " + ex.getStatusText());
		} catch (ResourceAccessException ex) {
			console.printError(ex.getMessage());
		}

		return transfer;
	}

	public TransferClient createTransfer(TransferClient transfer, BigDecimal amount, int userToId, int userFromId) {
		transfer.setAmount(amount);
		transfer.setUserToId(userToId);
		transfer.setUserFromId(userFromId);
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwt);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity request = new HttpEntity<>(transfer, headers);
		try {
			restTemplate.postForObject(API_BASE_URL + "/transfers", request, TransferClient.class);
		} catch (RestClientResponseException ex) {
			console.printError(ex.getRawStatusCode() + " : " + ex.getStatusText());
		} catch (ResourceAccessException ex) {
			console.printError(ex.getMessage());
		}
		return transfer;
	}

	public List<TransferClient> getTransfers(int userId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwt);
		HttpEntity request = new HttpEntity<>(headers);
		List<TransferClient> transfers = null;
		try {
			TransferClient[] transferArray = restTemplate.exchange(API_BASE_URL + "/transfers/" + userId, HttpMethod.GET, request, TransferClient[].class).getBody();
			transfers = Arrays.asList(transferArray);
		} catch (RestClientResponseException ex) {
			console.printError(ex.getRawStatusCode() + " : " + ex.getStatusText());
		} catch (ResourceAccessException ex) {
			console.printError(ex.getMessage());
		}
		return transfers;
	}

	public List<TransferClient> getTransferDetails(Integer transferID) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwt);
		HttpEntity request = new HttpEntity<>(headers);
		List<TransferClient> transfers = null;
		try {
			String url = API_BASE_URL + "/transfers/" + transferID +"/details";

			TransferClient[] transferArray = restTemplate.exchange(url, HttpMethod.GET, request, TransferClient[].class).getBody();
			transfers = Arrays.asList(transferArray);
		} catch (RestClientResponseException ex) {
			console.printError(ex.getRawStatusCode() + " : " + ex.getStatusText());
		} catch (ResourceAccessException ex) {
			console.printError(ex.getMessage());
		}
		return transfers;

	}
}
