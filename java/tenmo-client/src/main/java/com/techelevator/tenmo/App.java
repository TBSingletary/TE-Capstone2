package com.techelevator.tenmo;

import java.math.BigDecimal;
import java.util.Scanner;

import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.UserCredentials;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.TransferService;
import com.techelevator.tenmo.services.UserService;
import com.techelevator.view.ConsoleService;

public class App {

	private static final String API_BASE_URL = "http://localhost:8080/";

	private static final String MENU_OPTION_EXIT = "Exit";
	private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN,
			MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS,
			MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS,
			MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };

	private AuthenticatedUser currentUser;
	private ConsoleService console;
	private AuthenticationService authenticationService;
	private UserService userService;
	private AccountService accountService;
	private TransferService transferService;

	public static void main(String[] args) {
		App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL),
				new UserService(API_BASE_URL), new AccountService(API_BASE_URL), new TransferService(API_BASE_URL));
		app.run();
	}

	public App(ConsoleService console, AuthenticationService authenticationService, UserService userService,
			AccountService accountService, TransferService transferService) {
		this.console = console;
		this.authenticationService = authenticationService;
		this.userService = userService;
		this.accountService = accountService;
		this.transferService = transferService;
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
		while (true) {
			String choice = (String) console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if (MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if (MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if (MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if (MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if (MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if (MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() {
		BigDecimal accountBalance;
		accountBalance = accountService.getAccountBalance(currentUser.getToken());
		System.out.println(accountBalance);
	}

	private void viewTransferHistory() {
		//CALL TRANSFER SERVICE TO GET TRANSFER HISTORY
		Transfer[] allTransfers = transferService.getTransferHistory(currentUser.getToken(),
				currentUser.getUser().getId());

		System.out.println("----------------------------------------------");
		System.out.println("Transfers");
		System.out.println("ID           From/To           Amount");
		System.out.println("----------------------------------------------");

		//LOOP THROUGH EACH TRANSFER AND PRINT THEM OUT IF THEY ARE ASSOCIATED WITH THE CURRENT USER
		for (int i = 0; i < allTransfers.length; i++) {
			if (allTransfers[i].getToUserId() == currentUser.getUser().getId()) {
				System.out.println(allTransfers[i].getTransferId() + "          From: "
						+ allTransfers[i].getFromUsername() + "          $ " + allTransfers[i].getTransferAmount());
			} else if (allTransfers[i].getFromUserId() == currentUser.getUser().getId()) {
				System.out.println(allTransfers[i].getTransferId() + "          To: " + allTransfers[i].getToUserName()
						+ "          $ " + allTransfers[i].getTransferAmount());
			}
		}

		Scanner input = new Scanner(System.in);
		System.out.println("Please enter the 'Transfer ID' to retreive details of the transfer or (0) to exit>>>>");
		String stringTransferId = input.nextLine();
		boolean flagFoundId = false;
		long transfersId = 0;
		BigDecimal transferAmount = new BigDecimal(0);
		String fromUserName = "";
		String toUserName = "";
		String typeOfTransfer = "";
		String statusOfTransfer = "";


		//DISPLAY TRANSFER DETAILS FOR SELECTED TRANSFER, IF VALID
		while (true && !String.valueOf(stringTransferId).equals(String.valueOf(0))) {

			for (int i = 0; i < allTransfers.length; i++) {
				if (String.valueOf(stringTransferId).equals(String.valueOf(allTransfers[i].getTransferId()))) {
					transfersId = allTransfers[i].getTransferId();
					fromUserName = allTransfers[i].getFromUsername();
					toUserName = allTransfers[i].getToUserName();
					typeOfTransfer = allTransfers[i].getTypeOfTransfer();
					statusOfTransfer = allTransfers[i].getStatusOfTransfer();
					transferAmount = allTransfers[i].getTransferAmount();
					flagFoundId = true;
					stringTransferId = "0";
					break;
				}
			}

			if (flagFoundId == false) {
				System.out.println(
						"Please enter the valid 'Transfer ID' to retreive details of the transfer or (0) to exit>>>>");
				stringTransferId = input.nextLine();
			}
		}

		if (flagFoundId == true) {
			System.out.println("----------------------------------------------");
			System.out.println("Transfer Details");
			System.out.println("----------------------------------------------");
			System.out.println("Id: " + transfersId);
			System.out.println("From: " + fromUserName);
			System.out.println("To: " + toUserName);
			System.out.println("Type: " + typeOfTransfer);
			System.out.println("Status: " + statusOfTransfer);
			System.out.println("Amount: " + transferAmount);
		}
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
	}

	private void sendBucks() {
		// LISTING ALL USERS AND IDS
		console.printUsers(userService.getAll(currentUser.getToken()));
		System.out.println("");

		Boolean validResponse = false;
		int selectedUserId = -1;
		BigDecimal transferAmount = new BigDecimal(0);
		BigDecimal zero = new BigDecimal(0);
		BigDecimal currentBalance = (accountService.getAccountBalance(currentUser.getToken()));
		Transfer transfer = new Transfer();

		while (true) {
			//ASK WHICH USER TO SEND MONEY TO OR EXIT
			selectedUserId = console.getUserInputInteger("Enter ID of user you are sending to (0 to cancel)");
			if (selectedUserId == 0) {
				break;
			}
			if (selectedUserId > 0 && selectedUserId <= userService.getAll(currentUser.getToken()).length) {
				//PROMPT FOR AMOUND TO SEND
				transferAmount = console.getUserInputBigDecimal("Enter amount");
				
				//TRANSFER MONEY
				if (transferAmount.compareTo(zero) == 1 && transferAmount.compareTo(currentBalance) <= 0) {
					transfer.setFromUserId(currentUser.getUser().getId());
					transfer.setToUserId(selectedUserId);
					transfer.setTransferAmount(transferAmount);
					transfer.setStatusOfTransferId(2);
					transfer.setTypeOfTransferId(2);

					transferService.createTransfer(currentUser.getToken(), transfer);
					System.out.println("\nTransfer Complete!");
					break;
				}
				System.out.println("\nInsufficient Funds! Please try again.\n");
			}
		}

	}

	private void requestBucks() {
		// TODO Auto-generated method stub

	}

	private void exitProgram() {
		System.out.println("Thank you for choosing TEnmo!");
		System.exit(0);
	}

	private void registerAndLogin() {
		while (!isAuthenticated()) {
			String choice = (String) console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
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
		while (!isRegistered) //LOOPS UNTIL USER IS REGISTERED
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				authenticationService.register(credentials);
				isRegistered = true;
				System.out.println("Registration successful. You can now login.");
			} catch (AuthenticationServiceException e) {
				System.out.println("REGISTRATION ERROR: " + e.getMessage());
				System.out.println("Please attempt to register again.");
			}
		}
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //LOOPS UNTIL USER IS LOGGED IN
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: " + e.getMessage());
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