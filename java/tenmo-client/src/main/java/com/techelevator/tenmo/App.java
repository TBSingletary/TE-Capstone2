package com.techelevator.tenmo;


import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.models.AccountClient;
import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.TransferClient;
import com.techelevator.tenmo.models.UserClient;
import com.techelevator.tenmo.models.UserCredentials;
import com.techelevator.tenmo.services.AccountBalanceService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.TransferService;
import com.techelevator.tenmo.services.UserService;
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

	private AuthenticatedUser currentUser;
	private ConsoleService console;
	private AuthenticationService authenticationService;
	private AccountBalanceService accountBalanceService;
	private TransferService transferService;
	private UserService userService;

	public static void main(String[] args) {
		App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
		app.run();
	}

	public App(ConsoleService console, AuthenticationService authenticationService) {
		this.console = console;
		this.authenticationService = authenticationService;
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

		AccountClient account = accountBalanceService.getBalance();
		String message = String.format("Balance $%6.2f", account.getBalance());
		console.displayMessage(message);
	}

	private void viewTransferHistory() {

		Integer transferID = console.displayTransfers(transferService.getTransfers(accountBalanceService.getBalance().getAccountId()));
		if (transferID == 0) {
			mainMenu();
		} else if (transferID == -1) {
			viewTransferHistory();
		} else {
			List<TransferClient> transfer = transferService.getTransferDetails(transferID);
			console.displayTransferDetails(transfer);
		}
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub

	}

	private void sendBucks() {
		List<UserClient> users = transferService.getAllUsers();
		Integer userId = console.displayAllUsers(users);

		if (userId == 0) {
			mainMenu();
		} else if (userId == -1) {
			sendBucks();
		} else {
			TransferClient transfer = console.createTransfer(currentUser.getUser().getId(), userId);

			AccountClient account = accountBalanceService.getBalance();
			if (transfer.getAmount().doubleValue() <= 0) {
				System.out.println("Enter number larger than 0");
				sendBucks();
			}

			else if (account.getBalance().doubleValue() >= transfer.getAmount().doubleValue()) {
				transferService.updateBalance(transfer);
				transferService.createTransfer(transfer, transfer.getAmount(), transfer.getUserToId(),
						transfer.getUserToId());
			} else {
				System.out.println("Please enter an amount less than $" + account.getBalance());
				sendBucks();
			}
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

}
