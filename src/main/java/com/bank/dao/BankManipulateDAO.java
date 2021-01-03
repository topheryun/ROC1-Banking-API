package com.bank.dao;

import com.bank.exception.BusinessException;
import com.bank.model.Account;
import com.bank.model.Customer;

public interface BankManipulateDAO {
	
	public boolean createNewTransactionalAccount(Account account) throws BusinessException;
	public boolean registerNewCustomerAccount(Customer customer, String password) throws BusinessException;
	public boolean updateAccount(Account account, float amount) throws BusinessException;
	public boolean createTransfer(int id, Account targetAccount, float amount) throws BusinessException;
	public boolean deleteTransfer(Account transfer) throws BusinessException;
	public boolean approveCustomerAccount(Customer customer) throws BusinessException;
	public boolean deleteCustomerAccount(Customer customer) throws BusinessException;
	public boolean approveTransactionalAccount(Account account) throws BusinessException;
	public boolean deleteTransactionalAccount(Account account) throws BusinessException;

}
