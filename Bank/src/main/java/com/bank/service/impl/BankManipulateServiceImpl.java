package com.bank.service.impl;

import java.util.Random;

import org.apache.log4j.Logger;

import com.bank.dao.BankManipulateDAO;
import com.bank.dao.BankSearchDAO;
import com.bank.dao.impl.BankManipulateDAOImpl;
import com.bank.dao.impl.BankSearchDAOImpl;
import com.bank.exception.BusinessException;
import com.bank.model.Account;
import com.bank.model.Customer;
import com.bank.service.BankLogService;
import com.bank.service.BankManipulateService;

public class BankManipulateServiceImpl implements BankManipulateService {
	
	private static Logger log = Logger.getLogger(BankManipulateServiceImpl.class);
	BankSearchDAO bankSearchDAO = new BankSearchDAOImpl();
	BankManipulateDAO bankManipulateDAO = new BankManipulateDAOImpl();
	BankLogService bankLogService = new BankLogServiceImpl();

	@Override
	public boolean createNewTransactionalAccount(String userName, float balance) throws BusinessException {
		boolean isCreated = false;
		if (balance > 0) {
			Random random = new Random();
			int randomAccountNumber = 0;
			boolean isUnique = false;
			while (!isUnique) {
				randomAccountNumber = random.nextInt(8999) + 1000;
				isUnique = bankSearchDAO.checkForUniqueAccountNumber(randomAccountNumber);
			}
			Account account = new Account(userName, randomAccountNumber, balance);
			isCreated = bankManipulateDAO.createNewTransactionalAccount(account);
		}
		else {
			log.warn("Invalid balance. Must be greater than zero.");
		}
		return isCreated;
	}

	@Override
	public boolean withdrawFromAccount(int accountNumber, float amount) throws BusinessException {
		boolean isWithdrawn = false;
		Account account = bankSearchDAO.getAccount(accountNumber);
		if (account != null) {
			if (account.getBalance() >= amount && amount > 0) {
				amount = account.getBalance() - amount;
				isWithdrawn = bankManipulateDAO.updateAccount(account, amount);
				log.info(String.format("New balance: $%.2f", amount));
			}
			else {
				log.warn("Invalid amount. Must be less than current balance.");
			}
		}
		if (isWithdrawn) {
			bankLogService.addToLog("withdraw", amount-account.getBalance(), accountNumber);
		}
		return isWithdrawn;
	}

	@Override
	public boolean depositToAccount(int accountNumber, float amount) throws BusinessException {
		boolean isDeposited = false;
		Account account = bankSearchDAO.getAccount(accountNumber);
		if (account != null) {
			if (amount > 0) {
				amount += account.getBalance();
				isDeposited = bankManipulateDAO.updateAccount(account, amount);
				log.info(String.format("New balance: $%.2f", amount));
			}
			else {
				log.warn("Invalid amount. Must be greater than zero.");
			}
		}
		else {
			log.warn("Account not found.");
		}
		if (isDeposited) {
			bankLogService.addToLog("deposit", amount-account.getBalance(), accountNumber);
		}
		return isDeposited;
	}

	@Override
	public boolean transferMoney(int accountNumber, int targetAccountNumber, float amount) throws BusinessException {
		boolean isTransferedFrom = false;
		boolean transferCreated = false;
		Account account = bankSearchDAO.getAccount(accountNumber);
		Account targetAccount = bankSearchDAO.getAccount(targetAccountNumber);
		
		if (amount > 0) {
			if (account != null && targetAccount != null && account.getBalance() >= amount) {
				if (targetAccountNumber == targetAccount.getAccountNumber()) {
					Random random = new Random();
					int randomId = 0;
					boolean isUnique = false;
					while (!isUnique) {
						randomId = random.nextInt(8999) + 1000;
						isUnique = bankSearchDAO.checkForUniqueId(randomId);
					}
					isTransferedFrom = bankManipulateDAO.updateAccount(account, account.getBalance() - amount);
					transferCreated = bankManipulateDAO.createTransfer(randomId, targetAccount, amount);
					log.info(String.format("Transfering: $%.2f", amount));
				}
				else {
					log.warn("Could not locate target account number.");
				}
			}
			else {
				log.warn("Invalid amount. Must be less than current balance.");
			}
		}
		else {
			log.warn("Transfer amount must be greater than zero.");
		}
		if (isTransferedFrom && transferCreated) {
			bankLogService.addToLog("transferTo", amount, accountNumber);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean receiveTransfer(Account transferAccount) throws BusinessException {
		Account account = bankSearchDAO.getAccount(transferAccount.getAccountNumber());
		boolean isTransfered = bankManipulateDAO.updateAccount(
			account, 
			transferAccount.getBalance() + account.getBalance()
		);
		boolean isUpdated = bankManipulateDAO.deleteTransfer(transferAccount);
		if (isTransfered && isUpdated) {
			bankLogService.addToLog("transferFrom", account.getBalance(), transferAccount.getAccountNumber());
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean finalizePendingCustomerAccount(Customer customer, boolean isApproved) throws BusinessException {
		boolean isFinalized = false;
		if (isApproved) {
			isFinalized = bankManipulateDAO.approveCustomerAccount(customer);
			log.info("Customer Account has been approved.");
		}
		else {
			isFinalized = bankManipulateDAO.deleteCustomerAccount(customer);
			log.info("Customer Account has been denyed.");
		}
		return isFinalized;
	}

	@Override
	public boolean registerNewCustomerAccount(Customer customer, String password) throws BusinessException {
		boolean isRegistered = false;
		if (customer != null && customer.getContact() >= 1000000000L && customer.getContact() <= 9999999999L) {
			isRegistered = bankManipulateDAO.registerNewCustomerAccount(customer, password);
		}
		else {
			log.warn("Invalid contact. Must be 10 digits.");
		}
		return isRegistered;
	}

	@Override
	public boolean finalizePendingTransactionalAccount(Account account, boolean isApproved) throws BusinessException {
		boolean isFinalized = false;
		if (isApproved) {
			isFinalized = bankManipulateDAO.approveTransactionalAccount(account);
			log.info("Transactional Account has been approved.");
			bankLogService.addToLog("deposit", account.getBalance(), account.getAccountNumber());
		}
		else {
			isFinalized = bankManipulateDAO.deleteTransactionalAccount(account);
			log.info("Transactional Account has been denyed.");
		}
		return isFinalized;
	}
	
}
