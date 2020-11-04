package com.techelevator.tenmo.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;




public class UserServices {

	public static String AUTH_TOKEN = "";
	private String BASE_URL;
	private RestTemplate restTemplate = new RestTemplate();
	
	
	public double getCurrentBalance()
	{
		double balance = 0.0;
		return balance;
	}
	
	public void getTransferHistory()
	{
		//TODO View and accepted transfers
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
	
	private HttpEntity makeAuthEntity()
	{
		HttpHeaders headers = new HttpHeaders();		
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}
}
