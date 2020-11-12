package com.bank.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.bank.exception.BusinessException;
import com.bank.model.Account;
import com.bank.model.Log;

public interface BankLogDAO {
	
	public int getMaxId() throws BusinessException;
	public void addToLog(int id, String type, float amount, LocalDateTime ldt, int accountNumber) throws BusinessException;
	public List<Log> getAccountHistory(Account account) throws BusinessException;

}
