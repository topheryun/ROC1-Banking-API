package com.bank.dao;

import java.util.List;

import com.bank.exception.BusinessException;
import com.bank.model.Account;
import com.bank.model.Customer;

public interface BankSearchDAO {
	
	public boolean verifyUserLogin(String userName, String password) throws BusinessException;
	public boolean verifyEmployeeLogin(int employeeId, String password) throws BusinessException;
	public boolean checkForUniqueAccountNumber(int accountNumber) throws BusinessException;
	public List<Account> getAllTransactionalAccounts(String userName) throws BusinessException;
	public Account getAccount(int accountNumber) throws BusinessException;
	public boolean checkForUniqueId(int id) throws BusinessException;
	public List<Account> getAllTransfers(List<Account> accountsList) throws BusinessException;
	public List<Customer> getAllPendingCustomerAccounts() throws BusinessException;
	public List<Account> getAllPendingTransactionalAccounts() throws BusinessException;
	public List<Customer> getAllCustomerAccounts() throws BusinessException;
	
}
