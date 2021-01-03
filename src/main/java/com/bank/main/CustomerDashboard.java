package com.bank.main;

import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.bank.exception.BusinessException;
import com.bank.main.util.UI;
import com.bank.model.Account;
import com.bank.service.BankManipulateService;
import com.bank.service.BankSearchService;
import com.bank.service.impl.BankManipulateServiceImpl;
import com.bank.service.impl.BankSearchServiceImpl;

public class CustomerDashboard {
	
	private static Logger log = Logger.getLogger(CustomerDashboard.class);

	public static void viewCustomerDashboard(Scanner scanner, String userName) {
		int userChoice = -1;
		
		do {
			userChoice = -1;
			UI.printConsoleMenuItem("Customer Options");
			log.info("1. Create New Account.");
			log.info("2. View Accounts.");
			log.info("3. Withdraw Money.");
			log.info("4. Deposit Money.");
			log.info("5. Transfer Money.");
			log.info("6. Receive Transfer.");
			log.info("0. Exit.");
			try {
				userChoice = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {	
			}
			
			switch (userChoice) {
			case 1:
				viewCreateNewTransactionalAccountRoute(scanner, userName);
				break;
			case 2:
				viewAccountsRoute(scanner, userName);
				break;
			case 3:
				viewWithdrawMoneyRoute(scanner, userName);
				break;
			case 4:
				viewDepositMoneyRoute(scanner, userName);
				break;
			case 5:
				viewTransferMoneyRoute(scanner, userName);
				break;
			case 6: 
				viewReceiveTransferRoute(scanner, userName);
				break;
			case 0:
				log.info("Returning to main menu.");
				break;
			default:
				log.info("Please enter a number between 1 and 6.");
				break;
			}
			log.info("");
		} while(userChoice != 0);
	}
	
	public static void viewCreateNewTransactionalAccountRoute(Scanner scanner, String userName) {
		BankManipulateService bankManipulateService = new BankManipulateServiceImpl();
		float balance = 0;
		
		UI.printConsoleMenuItem("Create New Account");
		try {
			log.info("Deposit initial balance for new account.");
			balance = Float.parseFloat(scanner.nextLine());
			Boolean checkCreateAccount = bankManipulateService.createNewTransactionalAccount(userName, balance);
			if (checkCreateAccount) {
				log.info("Account Creation Successful. Pending Approval by Employee.");
			}
			else {
				log.warn("Account Creation Failed.");
			}
		} catch (BusinessException e) {
			log.error(e.getMessage());
		} catch (NumberFormatException e) {
			log.error("Must input a number.");
		}
	}
	
	public static void viewAccountsRoute(Scanner scanner, String userName) {
		BankSearchService bankSearchService = new BankSearchServiceImpl();
		
		UI.printConsoleMenuItem("Accounts");
		try {
			List<Account> accountList = bankSearchService.getAllTransactionalAccounts(userName);
			if (accountList != null & accountList.size() > 0) {
				for (Account account: accountList) {
					log.info(account);
				}
			}
		} catch (BusinessException e) {
			log.error(e.getMessage());
		}
	}
	
	public static void viewWithdrawMoneyRoute(Scanner scanner, String userName) {
		BankSearchService bankSearchService = new BankSearchServiceImpl();
		BankManipulateService bankManipulateService = new BankManipulateServiceImpl();
		int userAccountChoice = 0;
		float userWithdrawAmount = 0;
		
		UI.printConsoleMenuItem("Withdraw Money");
		try {
			log.info("Select Account.");
			List<Account> accountList = bankSearchService.getAllTransactionalAccounts(userName);
			if (accountList != null & accountList.size() > 0) {
				int i = 1;
				for (Account account: accountList) {
					log.info(i++ + ". " + account);
				}
				userAccountChoice = Integer.parseInt(scanner.nextLine());
				if (userAccountChoice > accountList.size()) {
					log.warn("Must select a number from the list.");
				}
				else if (userAccountChoice != 0) {
					log.info("How much would you like to withdraw?");
					userWithdrawAmount = Float.parseFloat(scanner.nextLine());
					boolean checkWithdraw = 
						bankManipulateService.withdrawFromAccount(
							accountList.get(userAccountChoice-1).getAccountNumber(), 
							userWithdrawAmount
						);
					if (checkWithdraw) {
						log.info(String.format("$%.2f has been withdrawan from your account.", userWithdrawAmount));
					}
					else {
						log.warn("Could not withdraw from account.");
					}
				}
			}
		} catch (BusinessException e) {
			log.error(e.getMessage());
		} catch (NumberFormatException e) {	
			log.error("Must input a number.");
		} catch (IndexOutOfBoundsException e) {
			log.error("That account does not exist.");
		}
	}
	
	public static void viewDepositMoneyRoute(Scanner scanner, String userName) {
		BankSearchService bankSearchService = new BankSearchServiceImpl();
		BankManipulateService bankManipulateService = new BankManipulateServiceImpl();
		int userAccountChoice = 0;
		float userDepositAmount = 0;
		
		UI.printConsoleMenuItem("Deposit Money");
		try {
			log.info("Select Account.");
			List<Account> accountList = bankSearchService.getAllTransactionalAccounts(userName);
			if (accountList != null & accountList.size() > 0) {
				int i = 1;
				for (Account account: accountList) {
					log.info(i++ + ". " + account);
				}
				userAccountChoice = Integer.parseInt(scanner.nextLine());
				if (userAccountChoice > accountList.size()) {
					log.warn("Must select a number from the list.");
				}
				else if (userAccountChoice != 0) {
					log.info("How much would you like to deposit?");
					userDepositAmount = Float.parseFloat(scanner.nextLine());
					boolean checkDeposit = 
						bankManipulateService.depositToAccount(
							accountList.get(userAccountChoice-1).getAccountNumber(), 
							userDepositAmount
						);
					if (checkDeposit) {
						log.info(String.format("$%.2f has been deposited to your account.", userDepositAmount));
					}
					else {
						log.warn("Could not deposit from account.");
					}
				}
			}
		} catch (BusinessException e) {
			log.error(e.getMessage());
		} catch (NumberFormatException e) {	
			log.error("Must input a number.");
		} catch (IndexOutOfBoundsException e) {
			log.error("That account does not exist.");
		}
	}

	public static void viewTransferMoneyRoute(Scanner scanner, String userName) {
		BankManipulateService bankManipulateService = new BankManipulateServiceImpl();
		BankSearchService bankSearchService = new BankSearchServiceImpl();
		int userAccountChoice = 0;
		int userTransferTarget = 0;
		float userTransferAmount = 0;
		
		UI.printConsoleMenuItem("Transfer Money");
		try {
			log.info("Select Account.");
			List<Account> accountList = bankSearchService.getAllTransactionalAccounts(userName);
			if (accountList != null & accountList.size() > 0) {
				int i = 1;
				for (Account account: accountList) {
					log.info(i++ + ". " + account);
				}
				userAccountChoice = Integer.parseInt(scanner.nextLine());
				if (userAccountChoice > accountList.size()) {
					log.warn("Must select a number from the list.");
				}
				else if (userAccountChoice != 0) {
					log.info("Enter account number to transfer to.");
					userTransferTarget = Integer.parseInt(scanner.nextLine());
					log.info("Enter amount to be transfered.");
					userTransferAmount = Float.parseFloat(scanner.nextLine());
					boolean checkTransfer = 
						bankManipulateService.transferMoney(
							accountList.get(userAccountChoice-1).getAccountNumber(),
							userTransferTarget,
							userTransferAmount
						);
					if (checkTransfer) {
						log.info(String.format("$%.2f is transfering to account %d.", userTransferAmount, userTransferTarget));
					}
					else {
						log.warn("Could not transfer.");
					}
				}
			}
		} catch (BusinessException e) {
			log.error(e.getMessage());
		} catch (NumberFormatException e) {	
			log.error("Must input a number.");
		} catch (IndexOutOfBoundsException e) {
			log.error("That account does not exist.");
		}
	}
	
	public static void viewReceiveTransferRoute(Scanner scanner, String userName) {
		BankManipulateService bankManipulateService = new BankManipulateServiceImpl();
		BankSearchService bankSearchService = new BankSearchServiceImpl();
		int userTransferChoice = 0;
		
		UI.printConsoleMenuItem("Receive Transfer");
		try {
			log.info("Incoming Transfers.");
			List<Account> transfersList = bankSearchService.getAllTransfers(userName);
			if (transfersList != null & transfersList.size() > 0) {
				int i = 1;
				for (Account transfer: transfersList) {
					log.info(i++ + ". " + transfer);
				}
				userTransferChoice = Integer.parseInt(scanner.nextLine());
				if (userTransferChoice > transfersList.size()) {
					log.warn("Must select a number from the list.");
				}
				else if (userTransferChoice != 0) {
					boolean checkTransfer = 
							bankManipulateService.receiveTransfer(transfersList.get(userTransferChoice-1));
					if (checkTransfer) {
						log.info("Transfer received.");
					}
					else {
						log.info("Could not receive transfer.");
					}
				}
			}
		} catch (BusinessException e) {
			log.error(e.getMessage());
		}
	}
	
}
