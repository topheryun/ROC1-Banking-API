package com.bank.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.bank.exception.BusinessException;
import com.bank.service.ValidationService;

class ValidationServiceImplTest {
	
	private static Logger log = Logger.getLogger(ValidationServiceImplTest.class);	
	private static ValidationService vs;
	
	@BeforeAll
	public static void setUp() {
		vs = new ValidationServiceImpl();
	}

	@Test
	void testVerifyUserLoginTrue() {
		boolean check = false;
		try {
			check = vs.verifyUserLogin("toph","pass");
		} catch (BusinessException e) {
			log.debug(e);
		}
		assertTrue(check);
	}
	
	@Test
	void testVerifyUserLoginFalsePassword() {
		boolean check = false;
		try {
			check = vs.verifyUserLogin("toph","pass123");
		} catch (BusinessException e) {
			log.debug(e);
		}
		assertFalse(check);
	}
	
	@Test
	void testVerifyUserLoginFalseUserName() {
		boolean check = false;
		try {
			check = vs.verifyUserLogin("toph123","pass");
		} catch (BusinessException e) {
			log.debug(e);
		}
		assertFalse(check);
	}

	@Test
	void testVerifyEmployeeLogin() {
		boolean check = false;
		try {
			check = vs.verifyEmployeeLogin(1234,"pass");
		} catch (BusinessException e) {
			log.debug(e);
		}
		assertTrue(check);
	}
	
	@Test
	void testVerifyEmployeeLoginFalsePassword() {
		boolean check = false;
		try {
			check = vs.verifyEmployeeLogin(1234,"pass123");
		} catch (BusinessException e) {
			log.debug(e);
		}
		assertFalse(check);
	}
	
	@Test
	void testVerifyEmployeeLoginFalseID() {
		boolean check = false;
		try {
			check = vs.verifyEmployeeLogin(12345,"pass");
		} catch (BusinessException e) {
			log.debug(e);
		}
		assertFalse(check);
	}

}
