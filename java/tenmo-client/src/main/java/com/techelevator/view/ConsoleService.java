package com.techelevator.view;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.techelevator.tenmo.models.TransferClient;
import com.techelevator.tenmo.models.UserClient;

public class ConsoleService {

	private PrintWriter out;
	private Scanner in;

	public ConsoleService(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output, true);
		this.in = new Scanner(input);
	}

	public int displayAllUsers(List<UserClient> users) {

		out.printf("%10s %10s", "Users ID", "Name");
		out.println();
		for (UserClient u : users) {
			out.printf("%10s %10s\n", u.getId(), u.getUsername());
		}
		out.println("Enter Id of user you are sending $ to (0 to cancel)");
		String userID = in.nextLine();
		ArrayList<Integer> userIds = new ArrayList<Integer>();
		for (UserClient user : users) {
			int id = user.getId();
			userIds.add(id);
		}
		Integer userId = Integer.parseInt(userID);
		if (userIds.contains(userId)) {
			return userId;
		} else if (userId == 0) {
			return 0;
		} else {
			System.out.println("Please enter a valid id");
			return -1;
		}

	}

	public TransferClient createTransfer(int userFromId, int userToId) {
		TransferClient transfer = new TransferClient();
		transfer.setUserFromId(userFromId);
		transfer.setUserToId(userToId);

		out.println("amount?");
		String amountToSend = in.nextLine();
		BigDecimal num = new BigDecimal(amountToSend);
		transfer.setAmount(num);

		return transfer;
	}

	public int displayTransfers(List<TransferClient> transfers) {

		out.printf("%-15s %-20s %-15s", "Transfer Id", "From/To", "Amount");
		out.println();
		out.println("---------------------------------------------------");

		for (TransferClient t : transfers) {
			out.println(t.getTransferId() + "              " + t.getFromOrTo() + t.getUsername() + "            "
					+ String.format("$%.2f", t.getAmount()));
		}

		out.println();
		out.println("Please enter transfer ID to view details (0 to cancel): ");
		String transferID = in.nextLine();
		ArrayList<Integer> transferIds = new ArrayList<Integer>();
		for (TransferClient transfer : transfers) {
			int id = transfer.getTransferId();
			transferIds.add(id);
		}
		Integer transferId = Integer.parseInt(transferID);
		if (transferIds.contains(transferId)) {
			return transferId;
		} else if (transferId == 0) {
			return 0;
		} else {
			System.out.println("Please enter a valid id");
			return -1;
		}
	}

	public void displayTransferDetails(List<TransferClient> transfers) {
		out.println("-------------------------------");
		out.println("Transfer Details");
		out.println("-------------------------------");

		for (TransferClient t : transfers) {
			out.println("ID: " + t.getTransferId());
			out.println("From: " + t.getUserNameFrom());
			out.println("To: " + t.getUserNameTo());
			out.println("Type: " + t.getTypeDescription());
			out.println("Status: " + t.getStatusDescription());
			out.println("Amount: $" + t.getAmount());
		}
	}

	public void displayMessage(String message) {
		out.println(message);
		out.flush();
	}

	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		out.println();
		return choice;
	}

	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption > 0 && selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if (choice == null) {
			out.println("\n*** " + userInput + " is not a valid option ***\n");
		}
		return choice;
	}

	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}

	public String getUserInput(String prompt) {
		out.print(prompt+": ");
		out.flush();
		return in.nextLine();
	}

	public Integer getUserInputInteger(String prompt) {
		Integer result = null;
		do {
			out.print(prompt+": ");
			out.flush();
			String userInput = in.nextLine();
			try {
				result = Integer.parseInt(userInput);
			} catch(NumberFormatException e) {
				out.println("\n*** " + userInput + " is not valid ***\n");
			}
		} while(result == null);
		return result;
	}

	public BigDecimal getUserInputBigDecimal(String prompt) {
		BigDecimal result = null;
		do {
			out.print(prompt+": ");
			out.flush();
			BigDecimal userInput = new BigDecimal(in.nextLine());
			try {
				result = (userInput);
			} catch(NumberFormatException e) {
				out.println("\n*** " + userInput + " is not valid ***\n");
			}
		} while(result == null);
		return result;
	}

	public void printError(String errorMessage) {
		System.err.println(errorMessage);
	}
}
