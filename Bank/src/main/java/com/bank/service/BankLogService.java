package com.bank.service;

import java.util.List;

import com.bank.exception.BusinessException;
import com.bank.model.Account;
import com.bank.model.Log;

public interface BankLogService {
	
	public void addToLog(String type, float amount, int accountNumber) throws BusinessException;
	public List<Log> getAccountHistory(Account account) throws BusinessException;

}
