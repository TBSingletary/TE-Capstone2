package com.techelevator.tenmo.services;

import java.math.BigDecimal;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;

public class TransferService {

	private final String BASE_SERVICE_URL ;
	 private RestTemplate restTemplate = new RestTemplate();
	 
	 public TransferService (String baseUrl) {
	    	this.BASE_SERVICE_URL = baseUrl + "account/";
	    }
		
	 public Transfer createTransfer(String authToken) {
	    	HttpEntity<?> entity = new HttpEntity<>(authHeaders(authToken));
	    	ResponseEntity<Transfer> response = restTemplate.exchange(BASE_SERVICE_URL + "transfer", HttpMethod.POST, entity, Transfer.class);
	    	return response.getBody();
	    	}

		

		private HttpHeaders authHeaders(String authToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(authToken);
			return headers;
		}
}
