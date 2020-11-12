package com.bank.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.log4j.Logger;

import com.bank.dao.BankLogDAO;
import com.bank.dao.impl.BankLogDAOImpl;
import com.bank.exception.BusinessException;
import com.bank.model.Account;
import com.bank.model.Log;
import com.bank.service.BankLogService;

public class BankLogServiceImpl implements BankLogService {
	
	private static Logger log = Logger.getLogger(BankLogServiceImpl.class);	
	BankLogDAO bankLogDAO = new BankLogDAOImpl();

	@Override
	public void addToLog(String type, float amount, int accountNumber) throws BusinessException {
		int id = bankLogDAO.getMaxId() + 1;
		LocalDateTime ldt = LocalDateTime.now();
		bankLogDAO.addToLog(id, type, amount, ldt, accountNumber);
	}

	@Override
	public List<Log> getAccountHistory(Account account) throws BusinessException {
		List<Log> logList = bankLogDAO.getAccountHistory(account);
		if (logList == null || logList.size() == 0) {
			log.warn("No Account History Available.");
		}
		return logList;
	}

}
