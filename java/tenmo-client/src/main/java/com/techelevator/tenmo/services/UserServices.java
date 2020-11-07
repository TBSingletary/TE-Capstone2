package com.techelevator.tenmo.services;

import java.math.BigDecimal;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.TransferClient;
import com.techelevator.tenmo.models.UserClient;


@RestController
//@PreAuthorize("isAuthenticated()")
public class UserServices {

	public static String AUTH_TOKEN = "";
	private String BASE_URL;
	private RestTemplate restTemplate = new RestTemplate();
	
	public UserServices (String url)
	{
		this.BASE_URL = url;
	}
	
	
	public BigDecimal getCurrentBalance(AuthenticatedUser currentUser)
	{
		ResponseEntity<BigDecimal> balance = restTemplate.exchange(BASE_URL + "accounts/balance", HttpMethod.GET, this.makeAuthEntity(currentUser), BigDecimal.class);
		return balance.getBody();
	}
	
//	public void getCurrentRequests()
//	{
//		//TODO Have the DAO call for any transfers with pending status
//	}
	
	public void sendMoney(AuthenticatedUser currentUser)
	{
		restTemplate.exchange(BASE_URL + "transfers", HttpMethod.POST, this.makeAuthEntity(currentUser), TransferClient.class);
		//TODO Choose an user to send money to. Search user, find account number and pay them. Throws an exception for a non-existent user
	}
	
//	public void requestMoney()
//	{
//		//TODO Shake down an existing user
//	}
	
		public TransferClient[] getTransferHistory(AuthenticatedUser currentUser)
	{
		ResponseEntity<TransferClient[]> transferList = restTemplate.exchange(BASE_URL + "transfers", HttpMethod.GET, this.makeAuthEntity(currentUser), TransferClient[].class);
		return transferList.getBody();
	}
	
	public UserClient[] getUserList()
	{		
		return restTemplate.getForObject(BASE_URL+ "users", UserClient[].class);
	}
	
	private HttpEntity makeAuthEntity(AuthenticatedUser currentUser)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(currentUser.getToken());
		HttpEntity entity = new HttpEntity(headers);
		return entity;
	}
}
