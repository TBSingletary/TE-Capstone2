package com.techelevator.tenmo;

import java.math.BigDecimal;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.AccountClient;
import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.TransferClient;
import com.techelevator.tenmo.models.UserClient;
import com.techelevator.tenmo.models.UserCredentials;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.UserServices;
import com.techelevator.view.ConsoleService;

@RestController
public class App {

	private static final String API_BASE_URL = "http://localhost:8080/";

	private static final String MENU_OPTION_EXIT = "Exit";
	private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };

	public static String AUTH_TOKEN = "";
	private RestTemplate restTemplate = new RestTemplate();
	private AuthenticatedUser currentUser;
	private ConsoleService console;
	private AuthenticationService authenticationService;
	private UserServices userServices;

	public static void main(String[] args) {
		App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL), new UserServices(API_BASE_URL));
		app.run();
	}

	public App(ConsoleService console, AuthenticationService authenticationService, UserServices userServices) {
		this.console = console;
		this.authenticationService = authenticationService;
		this.userServices = userServices;
	}

	public void run() {
		System.out.println(" ____ ____ ____ ____ ____");
		System.out.println("||T |||E |||n |||m |||o ||");
		System.out.println("||__|||__|||__|||__|||__||");
		System.out.println("|/__\\|/__\\|/__\\|/__\\|/__\\|");
		System.out.println();
		System.out.println("***************************");
		System.out.println(" *   Welcome to TEnmo!   * ");
		System.out.println("***************************");

		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() {

		System.out.print("Current Balance: $");
		System.out.println(userServices.getCurrentBalance(currentUser));	

	}

	private void viewTransferHistory() {

		TransferClient[] transfer = null;
		UserClient[] user = null;
		AccountClient account = null;

		try {
			System.out.println("---------------------------------");
			System.out.println("ID          From/To        Amount");
			System.out.println("---------------------------------");
			for(int i = 0; i < transfer.length; i++) {
				if(account.getAccountId() == transfer[i].getAccountTo()) {
					System.out.println(transfer[i].getTransferId() + "         From: " + transfer[i].getUsername() + "          $ " + transfer[i].getAmount());
				}else
					System.out.println(transfer[i].getTransferId() + "         To: " + transfer[i].getUsername() + "           $ " + transfer[i].getAmount());
			}
			System.out.println("---------------------------------");

		}catch (Exception ex) {
			System.out.println("No transfers found. Enter 0 to Exit to Main Menu.");
		}

		Integer detailId = console.getUserInputInteger("Please enter Transfer ID to view details");
		if(detailId == 0) {
			mainMenu();
		}

		AccountClient accounts = null;
		TransferClient transferDetail = null;
		TransferClient toTransferDetail = null;

		System.out.println("---------------------------------");
		System.out.println("Transfer Details");
		System.out.println("---------------------------------");
		System.out.println("Id: " + transferDetail.getTransferId());

		if(account.getAccountId() == transferDetail.getAccountFrom()) {
			System.out.println("From: " + currentUser.getUser().getUsername());
		}else
			System.out.println("From: " + transferDetail.getUsername());
		if(account.getAccountId() == transferDetail.getAccountTo()) {
			System.out.println("To: " + currentUser.getUser().getUsername());
		}else
			System.out.println("To: " + toTransferDetail.getUsername());
		if(transferDetail.getTransferTypeId() == 2) {
			System.out.println("Type: " + "Send");
		}else
			System.out.println("Type: " + "Request");
		if(transferDetail.getTransferStatusId() == 2) {
			System.out.println("Status: " + "Approved");
		}else
			if(transferDetail.getTransferStatusId() == 1) {
				System.out.println("Status: " + "Pending");
			}else
				System.out.println("Status: " + "Rejected");

		System.out.println("Amount: $" + transferDetail.getAmount());

	}

	private void viewPendingRequests() {		
		//OPTIONAL	
	}

	private void sendBucks() {		

		TransferClient transfer = new TransferClient();
		UserServices account = null;
		UserClient[] user = null;

		System.out.println("---------------------------------");
		System.out.println("ID     Name");
		System.out.println("---------------------------------");

		System.out.println(userServices.getUserList(currentUser));

		System.out.println("---------------------------------");

		transfer.setAccountFrom(currentUser.getUser().getId());
		transfer.setTransferId(1);
		transfer.setTransferStatusId(2);
		transfer.setTransferTypeId(2);

		Integer sendToId = console.getUserInputInteger("Please enter the ID of the user you are sending to");
		if(sendToId == 0) {
			mainMenu();
		}

		boolean balanceCheck = false;

		while(!balanceCheck) {
			BigDecimal amountToSend = console.getUserInputBigDecimal("Please enter the amount to send");
			if(amountToSend.compareTo(userServices.getCurrentBalance(currentUser)) == 1) {
				System.out.println("Sorry, insufficient funds.");
			}
			else {
				balanceCheck = true;
			}
			transfer.setAccountTo(sendToId);
			transfer.setAmount(amountToSend);

			transfer = restTemplate.postForObject(API_BASE_URL + "transfers/new/", makeTransferEntity(transfer), TransferClient.class);

		}


	}

	private void requestBucks() {
		// TODO Auto-generated method stub

	}

	private void exitProgram() {
		System.out.println("Thanks for choosing TEnmo!");
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
		while (!isRegistered) //will keep looping until user is registered
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				authenticationService.register(credentials);
				isRegistered = true;
				System.out.println("Registration successful. You can now login.");
			} catch(AuthenticationServiceException e) {
				System.out.println("REGISTRATION ERROR: "+e.getMessage());
				System.out.println("Please attempt to register again.");
			}
		}
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}

	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}

	private HttpEntity makeAuthEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity entity = new HttpEntity(headers);
		return entity;
	}

	private HttpEntity makeTransferEntity(TransferClient transfer) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity<TransferClient> entity = new HttpEntity(transfer, headers);
		return entity;
	}
}
