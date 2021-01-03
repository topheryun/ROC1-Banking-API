package com.bank.main;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.bank.exception.BusinessException;
import com.bank.main.util.UI;
import com.bank.model.Customer;
import com.bank.service.BankManipulateService;
import com.bank.service.ValidationService;
import com.bank.service.impl.BankManipulateServiceImpl;
import com.bank.service.impl.ValidationServiceImpl;

public class UserDashboard {
	
	private static Logger log = Logger.getLogger(UserDashboard.class);

	public static void viewUserLoginRoute() {
		Scanner scanner = new Scanner(System.in);
		log.info("Welcome to Chris' Super Cool Bank");
		int userChoice = -1;
		
		do {
			userChoice = -1;
			UI.printConsoleMenuItem("Login Menu");
			log.info("1. Customer Login");
			log.info("2. Employee Login");
			log.info("3. Register new account");
			log.info("0. Exit");
			try {
				userChoice = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {	
			}
			
			switch (userChoice) {
			case 1: 
				viewCustomerLoginRoute(scanner);
				break;
			case 2:
				viewEmployeeLoginRoute(scanner);
				break;
			case 3:
				viewCustomerRegisterRoute(scanner);
				break;
			case 0:
				log.info("Thank you for user Chris' Super Cool Bank uwu.");
				break;
			default:
				log.info("Please enter a number between 1 and 4.");
				break;
			}
			log.info("");
		} while(userChoice != 0);
		scanner.close();
	}

	public static void viewCustomerLoginRoute(Scanner scanner) {
		ValidationService validationService = new ValidationServiceImpl();
		String userName = "", password = "";
		
		UI.printConsoleMenuItem("Customer Login");
		try {
			log.info("Enter User Name.");
			userName = scanner.nextLine();
			log.info("Enter Password.");
			password = scanner.nextLine();
			Boolean checkUserLogin = validationService.verifyUserLogin(userName,password);
			if (checkUserLogin) {
				log.info("Login Successful!");
				CustomerDashboard.viewCustomerDashboard(scanner, userName);
			}
			else {
				log.warn("Login Failed.");
			}
		} catch (BusinessException e) {
			log.error(e.getMessage());
		}
	}
	
	public static void viewEmployeeLoginRoute(Scanner scanner) {
		ValidationService validationService = new ValidationServiceImpl();
		int employeeId = 0;
		String password = "";
		
		UI.printConsoleMenuItem("Employee Login");
		try {
			log.info("Enter Employee ID.");
			employeeId = Integer.parseInt(scanner.nextLine());
			log.info("Enter Password.");
			password = scanner.nextLine();
			Boolean checkEmployeeLogin = validationService.verifyEmployeeLogin(employeeId,password);
			if (checkEmployeeLogin) {
				log.info("Login Successful!");
				EmployeeDashboard.viewEmployeeDashboard(scanner);
			}
			else {
				log.warn("Login Failed.");
			}
		} catch (BusinessException e ) {
			log.error(e.getMessage());
		} catch (NumberFormatException e) {
			log.error("Must enter a proper Employee ID (number).");
		}
	}

	public static void viewCustomerRegisterRoute(Scanner scanner) {
		BankManipulateService bankManipulateService = new BankManipulateServiceImpl();
		String userName = "", firstName = "", lastName = "", password = "";
		Long contact = 0L;
		
		UI.printConsoleMenuItem("Customer Registration");
		try {
			log.info("Enter User Name.");
			userName = scanner.nextLine();
			log.info("Enter password.");
			password = scanner.nextLine();
			log.info("Enter First Name.");
			firstName = scanner.nextLine();
			log.info("Enter Last Name");
			lastName = scanner.nextLine();
			log.info("Enter 10-digit Phone Number");
			contact = Long.parseLong(scanner.nextLine());
			Customer customer = new Customer(userName, firstName, lastName, contact);
			Boolean checkRegistrationInfo = bankManipulateService.registerNewCustomerAccount(customer, password);
			if (checkRegistrationInfo) {
				log.info("Registration Pending. Waiting for approval from Employee.");
			}
			else {
				log.warn("Registration Failed.");
			}
		} catch (BusinessException e) {
			log.error(e.getMessage());
		} catch (NumberFormatException e) {
			log.error("Phone number must be a 10 digit number.");
		}
	}
	
}
