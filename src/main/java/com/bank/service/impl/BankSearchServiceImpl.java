package com.bank.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.bank.dao.BankSearchDAO;
import com.bank.dao.impl.BankSearchDAOImpl;
import com.bank.exception.BusinessException;
import com.bank.model.Account;
import com.bank.model.Customer;
import com.bank.service.BankSearchService;

public class BankSearchServiceImpl implements BankSearchService {
	
	private static Logger log = Logger.getLogger(BankSearchServiceImpl.class);	
	BankSearchDAO bankSearchDAO = new BankSearchDAOImpl();

	@Override
	public List<Account> getAllTransactionalAccounts(String userName) throws BusinessException {
		List<Account> accountsList = bankSearchDAO.getAllTransactionalAccounts(userName);
		if (accountsList.size() == 0) {
			log.warn("No Account Records Available.");
		}
		return accountsList;
	}

	@Override
	public List<Customer> getPendingCustomerAccounts() throws BusinessException {
		List<Customer> customersList = bankSearchDAO.getAllPendingCustomerAccounts();
		if (customersList.size() == 0) {
			log.warn("No Customers Available.");
		}
		return customersList;
	}

	@Override
	public List<Account> getAllTransfers(String userName) throws BusinessException {
		List<Account> accountsList = getAllTransactionalAccounts(userName);
		List<Account> transfersList = bankSearchDAO.getAllTransfers(accountsList);
		if (transfersList.size() == 0) {
			log.warn("No Transfers Available.");
		}
		return transfersList;
	}

	@Override
	public List<Account> getPendingTransactionalAccounts() throws BusinessException {
		List<Account> accountsList = bankSearchDAO.getAllPendingTransactionalAccounts();
		if (accountsList.size() == 0) {
			log.warn("No Transactional Accounts Available.");
		}
		return accountsList;
	}

	@Override
	public List<Customer> getAllCustomerAccounts() throws BusinessException {
		List<Customer> customersList = bankSearchDAO.getAllCustomerAccounts();
		if (customersList.size() == 0) {
			log.warn("No Customer Accounts Available.");
		}
		return customersList;
	}

}
