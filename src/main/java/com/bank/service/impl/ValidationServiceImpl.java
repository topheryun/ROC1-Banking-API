package com.bank.service.impl;

import com.bank.dao.BankSearchDAO;
import com.bank.dao.impl.BankSearchDAOImpl;
import com.bank.exception.BusinessException;
import com.bank.service.ValidationService;

public class ValidationServiceImpl implements ValidationService {
	
	BankSearchDAO bankSearchDAO = new BankSearchDAOImpl();
	
	@Override
	public boolean verifyUserLogin(String userName, String password) throws BusinessException {
		boolean checkUserLogin = false;
		if (userName != null && password != null) {
			checkUserLogin = bankSearchDAO.verifyUserLogin(userName, password);
		}
		else if (userName == null) {
			throw new BusinessException("Invalid user name.");
		}
		else if (password == null) {
			throw new BusinessException("Invalid password.");
		}
		return checkUserLogin;
	}

	@Override
	public boolean verifyEmployeeLogin(int employeeId, String password) throws BusinessException {
		boolean checkEmployeeLogin = false;
		if (employeeId != 0 && password != null) {
			checkEmployeeLogin = bankSearchDAO.verifyEmployeeLogin(employeeId, password);
		}
		else if (employeeId == 0) {
			throw new BusinessException("Invalid employee id.");
		}
		else if (password == null) {
			throw new BusinessException("Invalid password.");
		}
		return checkEmployeeLogin;
	}

}
