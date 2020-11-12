package com.bank.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bank.dao.BankLogDAO;
import com.bank.dao.util.BankDbQueries;
import com.bank.dao.util.BankDbUtilProps;
import com.bank.dao.util.BankPostgresSqlConnection;
import com.bank.exception.BusinessException;
import com.bank.model.Account;
import com.bank.model.Log;

public class BankLogDAOImpl implements BankLogDAO {
	
	private static Logger log = Logger.getLogger(BankLogDAOImpl.class);

	@Override
	public int getMaxId() throws BusinessException {
		int maxId = 0;
		try (Connection connection = BankPostgresSqlConnection.getConnection()) {
			String sql = BankDbQueries.GET_MAX_ID;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				maxId = resultSet.getInt("maxId");
			}
			else {
				log.warn("Error: No records exist.");
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException(BankDbUtilProps.ERROR_MESSAGE);
		}
		return maxId;
	}

	@Override
	public void addToLog(int id, String type, float amount, LocalDateTime ldt, int accountNumber)
			throws BusinessException {
		try (Connection connection = BankPostgresSqlConnection.getConnection()) {
			String sql = BankDbQueries.ADD_TO_LOG;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, type);
			preparedStatement.setFloat(3, amount);
			preparedStatement.setTimestamp(4, Timestamp.valueOf(ldt));
			preparedStatement.setInt(5, accountNumber);
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException(BankDbUtilProps.ERROR_MESSAGE);
		}
	}

	@Override
	public List<Log> getAccountHistory(Account account) throws BusinessException {
		List<Log> logList = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
		LocalDateTime ldt = null;
		try (Connection connection = BankPostgresSqlConnection.getConnection()) {
			String sql = BankDbQueries.GET_ACCOUNT_HISTORY;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, account.getAccountNumber());
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Timestamp temp = resultSet.getTimestamp("timestamp");
				ldt = temp.toLocalDateTime();
				String formattedDateTime = ldt.format(formatter);
				Log logRecord = new Log(
					resultSet.getInt("id"),
					resultSet.getString("type"),
					resultSet.getFloat("amount"),
					formattedDateTime
				);
				logList.add(logRecord);
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException(BankDbUtilProps.ERROR_MESSAGE);
		}
		return logList;
	}

}
