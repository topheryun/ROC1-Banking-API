package com.bank.service;

import com.bank.exception.BusinessException;

public interface ValidationService {
	
	public boolean verifyUserLogin(String userName, String password) throws BusinessException;
	public boolean verifyEmployeeLogin(int employeeId, String password) throws BusinessException;

}
