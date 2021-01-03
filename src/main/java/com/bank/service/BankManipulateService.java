package com.bank.service;

import com.bank.exception.BusinessException;
import com.bank.model.Account;
import com.bank.model.Customer;

public interface BankManipulateService {
	
	public boolean createNewTransactionalAccount(String userName, float balance) throws BusinessException;
	public boolean withdrawFromAccount(int accountNumber, float amount) throws BusinessException;
	public boolean depositToAccount(int accountNumber, float amount) throws BusinessException;
	public boolean transferMoney(int accountNumber, int targetAccountNumber, float amount) throws BusinessException;
	public boolean receiveTransfer(Account transferAccount) throws BusinessException;
	public boolean finalizePendingCustomerAccount(Customer customer, boolean isApproved) throws BusinessException;
	public boolean registerNewCustomerAccount(Customer customer, String password) throws BusinessException;
	public boolean finalizePendingTransactionalAccount(Account account, boolean isApproved) throws BusinessException;
	
}
