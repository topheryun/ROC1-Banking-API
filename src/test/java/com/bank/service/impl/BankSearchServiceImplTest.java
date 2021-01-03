package com.bank.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.bank.exception.BusinessException;
import com.bank.model.Account;
import com.bank.model.Customer;
import com.bank.service.BankSearchService;

class BankSearchServiceImplTest {
	
	private static Logger log = Logger.getLogger(BankSearchServiceImplTest.class);	
	private static BankSearchService bss;
	
	@BeforeAll
	public static void setUp() {
		bss = new BankSearchServiceImpl();
	}

	@Test
	void testGetAllTransactionalAccounts() {
		List<Account> accountsList = null;
		try {
			accountsList = bss.getAllTransactionalAccounts("toph");
		} catch (BusinessException e) {
			log.debug(e);
		}
		assertNotEquals(0, accountsList.size());
	}
	
	@Test
	void testGetAllTransactionalAccountsWrongUserName() {
		List<Account> accountsList = null;
		try {
			accountsList = bss.getAllTransactionalAccounts("topher");
		} catch (BusinessException e) {
			log.debug(e);
		}
		assertEquals(0, accountsList.size());
	}

	@Test
	void testGetPendingCustomerAccounts() {
		List<Customer> customersList = null;
		try {
			customersList = bss.getPendingCustomerAccounts();
		} catch (BusinessException e) {
			log.debug(e);
		}
		assertNotEquals(0, customersList.size());
	}

	@Test
	void testGetAllTransfers() {
		List<Account> accountsList = null;
		try {
			accountsList = bss.getAllTransfers("toph");
		} catch (BusinessException e) {
			log.debug(e);
		}
		assertNotEquals(0, accountsList.size());
	}
	
	@Test
	void testGetAllTransfersWrongUserName() {
		List<Account> accountsList = null;
		try {
			accountsList = bss.getAllTransfers("topher");
		} catch (BusinessException e) {
			log.debug(e);
		}
		assertEquals(0, accountsList.size());
	}

	@Test
	void testGetPendingTransactionalAccounts() {
		List<Account> accountsList = null;
		try {
			accountsList = bss.getPendingTransactionalAccounts();
		} catch (BusinessException e) {
			log.debug(e);
		}
		assertNotEquals(0, accountsList.size());
	}

	@Test
	void testGetAllCustomerAccounts() {
		List<Customer> customersList = null;
		try {
			customersList = bss.getAllCustomerAccounts();
		} catch (BusinessException e) {
			log.debug(e);
		}
		assertNotEquals(0, customersList.size());
	}

}
