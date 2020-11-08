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
	
	public TransferClient createTransfer(AuthenticatedUser currentUser, AuthenticatedUser userTo, BigDecimal amount)
	{		
		return restTemplate.exchange(BASE_URL + "transfers/new", HttpMethod.POST, makeAuthEntity(currentUser), TransferClient.class).getBody();
	}
	
	public void sendMoney(AuthenticatedUser currentUser, TransferClient transfer)
	{
		restTemplate.exchange(BASE_URL + "transfers/send", HttpMethod.PUT, makeAuthEntity(currentUser), TransferClient.class).getBody();
		//TODO Choose an user to send money to. Search user, find account number and pay them. Throws an exception for a non-existent user
	}
	
//	public void requestMoney()
//	{
//		//TODO Shake down an existing user
//	}
	
		public TransferClient[] getTransferHistory(AuthenticatedUser currentUser)
	{
		ResponseEntity<TransferClient[]> transferList = restTemplate.exchange(BASE_URL + "transfers/" + currentUser.getUser().getId() + "/getAll", HttpMethod.GET, this.makeAuthEntity(currentUser), TransferClient[].class);
		return transferList.getBody();
	}
	
	public void getUserList(AuthenticatedUser currentUser)
	{		
		ResponseEntity<UserClient[]> userList = restTemplate.exchange(BASE_URL+ "users", HttpMethod.GET, makeAuthEntity(currentUser), UserClient[].class);
		for(int i = 0; i < userList.getBody().length; i++)
		{
			System.out.println(userList.getBody()[i].toString());	
		}
	}
	
	public AuthenticatedUser findUserByUsername(String user, AuthenticatedUser currentUser)
	{		
		AuthenticatedUser otherUser = restTemplate.exchange(BASE_URL+ "users/" + user + "/find",HttpMethod.GET, makeAuthEntity(currentUser), AuthenticatedUser.class).getBody();
		return otherUser;
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
