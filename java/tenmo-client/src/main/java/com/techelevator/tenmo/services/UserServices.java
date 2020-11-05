package com.techelevator.tenmo.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.AuthenticatedUser;
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
	
	
	public double getCurrentBalance(AuthenticatedUser currentUser)
	{
		//TODO connect to db
		HttpEntity<UserClient> entity = makeAuthEntity(currentUser);
		double balance = restTemplate.postForObject(BASE_URL + "accounts/balance", entity, Double.class);
		return balance;
	}
	
	public void getTransferHistory()
	{
		//TODO View any accepted transfers
	}
	
	public void getCurrentRequests()
	{
		//TODO Have the DAO call for any transfers with pending status
	}
	
	public void sendMoney()
	{
		//TODO Choose an user to send money to. Search user, find account number and pay them. Throws an exception for a non-existent user
	}
	
	public void requestMoney()
	{
		//TODO Shake down an existing user
	}
	
	public UserClient[] getUserList()
	{		
		return restTemplate.getForObject(BASE_URL+ "users", UserClient[].class);
	}
	
	private HttpEntity<UserClient> makeAuthEntity(AuthenticatedUser currentUser)
	{
		HttpHeaders headers = new HttpHeaders();		
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity<UserClient> entity = new HttpEntity<UserClient>(headers);
		return entity;
	}
}
