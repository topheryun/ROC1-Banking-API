package com.bank.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.bank.exception.BusinessException;
import com.bank.service.BankManipulateService;

class BankManipulateServiceImplTest {
	
	private static Logger log = Logger.getLogger(BankManipulateServiceImplTest.class);	
	private static BankManipulateService bms;
	
	@BeforeAll
	public static void setUp() {
		bms = new BankManipulateServiceImpl();
	}
	
	@Test
	void testDepositToAccount() {
		boolean isDeposited = false;
		try {
			isDeposited = bms.depositToAccount(1111, 1);
		} catch (BusinessException e) {
			log.debug(e);
		}
		assertTrue(isDeposited);
	}

	@Test
	void testDepositToAccountWrongAccountNumber() {
		boolean isDeposited = false;
		try {
			isDeposited = bms.depositToAccount(11111, 1);
		} catch (BusinessException e) {
			log.debug(e);
		}
		assertFalse(isDeposited);
	}
	
	@Test
	void testWithdrawFromAccount() {
		boolean isWithdrawn = false;
		try {
			isWithdrawn = bms.withdrawFromAccount(1111, 1);
		} catch (BusinessException e) {
			log.debug(e);
		}
		assertTrue(isWithdrawn);
	}
	
	@Test
	void testWithdrawFromAccountWrongAccountNumber() {
		boolean isWithdrawn = false;
		try {
			isWithdrawn = bms.withdrawFromAccount(11111, 1);
		} catch (BusinessException e) {
			log.debug(e);
		}
		assertFalse(isWithdrawn);
	}
	
	@Test
	void testDepositNegativeAmountToAccount() {
		boolean isDeposited = false;
		try {
			isDeposited = bms.depositToAccount(1111, -1);
		} catch (BusinessException e) {
			log.debug(e);
		}
		assertFalse(isDeposited);
	}

	@Test
	void testWithdrawNegativeAmountFromAccount() {
		boolean isWithdrawn = false;
		try {
			isWithdrawn = bms.withdrawFromAccount(1111, -1);
		} catch (BusinessException e) {
			log.debug(e);
		}
		assertFalse(isWithdrawn);
	}
	
	@Test
	void testWithdrawAmountGreaterThanBalanceFromAccount() {
		boolean isWithdrawn = false;
		try {
			isWithdrawn = bms.withdrawFromAccount(1111, 100000);
		} catch (BusinessException e) {
			log.debug(e);
		}
		assertFalse(isWithdrawn);
	}

}
