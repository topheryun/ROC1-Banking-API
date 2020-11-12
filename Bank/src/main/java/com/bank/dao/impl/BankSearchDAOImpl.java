package com.bank.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bank.dao.BankSearchDAO;
import com.bank.dao.util.BankDbQueries;
import com.bank.dao.util.BankDbUtilProps;
import com.bank.dao.util.BankPostgresSqlConnection;
import com.bank.exception.BusinessException;
import com.bank.model.Account;
import com.bank.model.Customer;

public class BankSearchDAOImpl implements BankSearchDAO {
	
	private static Logger log = Logger.getLogger(BankSearchDAOImpl.class);

	@Override
	public boolean verifyUserLogin(String userName, String password) throws BusinessException {
		boolean checkUserLogin = false;
		try (Connection connection = BankPostgresSqlConnection.getConnection()) {
			String sql = BankDbQueries.VERIFY_CUSTOMER_LOGIN;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userName);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				if (!resultSet.getBoolean("isPending")) {
					if (resultSet.getString("password").equals(password)) {
						checkUserLogin = true;
					}
					else {
						log.warn("Wrong password.");
					}
				}
				else {
					log.warn("Account has yet to be approved by an Employee.");
				}
			}
			else {
				log.warn("Could not find user name.");
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException(BankDbUtilProps.ERROR_MESSAGE);
		}
		return checkUserLogin;
	}

	@Override
	public boolean verifyEmployeeLogin(int employeeId, String password) throws BusinessException {
		boolean checkEmployeeLogin = false;
		try (Connection connection = BankPostgresSqlConnection.getConnection()) {
			String sql = BankDbQueries.VERIFY_EMPLOYEE_LOGIN;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, employeeId);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				if (resultSet.getString("password").equals(password)) {
					checkEmployeeLogin = true;
				}
				else {
					log.warn("Wrong password.");
				}
			}
			else {
				log.warn("Could not find ID.");
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException(BankDbUtilProps.ERROR_MESSAGE);
		}
		return checkEmployeeLogin;
	}

	@Override
	public boolean checkForUniqueAccountNumber(int accountNumber) throws BusinessException {
		boolean isUnique = false;
		try (Connection connection = BankPostgresSqlConnection.getConnection()) {
			String sql = BankDbQueries.CHECK_ACCOUNT_NUMBER_DUPLICATE;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, accountNumber);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				isUnique = true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException(BankDbUtilProps.ERROR_MESSAGE);
		}
		return isUnique;
	}

	@Override
	public List<Account> getAllTransactionalAccounts(String userName) throws BusinessException {
		List<Account> accountsList = new ArrayList<>();
		try (Connection connection = BankPostgresSqlConnection.getConnection()) {
			String sql = BankDbQueries.GET_ALL_TRANSACTIONAL_ACCOUNTS;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userName);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				if (!resultSet.getBoolean("isPending")) {
					Account account = new Account(
						resultSet.getString("userName"),
						resultSet.getInt("accountNumber"),
						resultSet.getFloat("balance")
					);
					accountsList.add(account);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException(BankDbUtilProps.ERROR_MESSAGE);
		}
		return accountsList;
	}

	@Override
	public Account getAccount(int accountNumber) throws BusinessException {
		Account account = null;
		try (Connection connection = BankPostgresSqlConnection.getConnection()) {
			String sql = BankDbQueries.GET_ACCOUNT;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, accountNumber);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				account = new Account(
					resultSet.getString("userName"),
					resultSet.getInt("accountNumber"),
					resultSet.getFloat("balance")
				);
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException(BankDbUtilProps.ERROR_MESSAGE);
		}
		return account;
	}

	@Override
	public boolean checkForUniqueId(int id) throws BusinessException {
		boolean isUnique = false;
		try (Connection connection = BankPostgresSqlConnection.getConnection()) {
			String sql = BankDbQueries.CHECK_ID_DUPLICATE;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				isUnique = true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException(BankDbUtilProps.ERROR_MESSAGE);
		}
		return isUnique;
	}

	@Override
	public List<Account> getAllTransfers(List<Account> accountsList) throws BusinessException {
		List<Account> transfersList = new ArrayList<>();
		try (Connection connection = BankPostgresSqlConnection.getConnection()) {
			String sql = BankDbQueries.GET_ALL_TRANSFERS;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			for (Account forAccount : accountsList) {
				preparedStatement.setInt(1, forAccount.getAccountNumber());
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					Account whileAccount = new Account(
						Integer.toString(resultSet.getInt("id")),
						resultSet.getInt("accountNumber"),
						resultSet.getFloat("amount")
					);
					transfersList.add(whileAccount);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException(BankDbUtilProps.ERROR_MESSAGE);
		}
		return transfersList;
	}

	@Override
	public List<Customer> getAllPendingCustomerAccounts() throws BusinessException {
		List<Customer> pendingCustomersList = new ArrayList<>();
		try (Connection connection = BankPostgresSqlConnection.getConnection()) {
			String sql = BankDbQueries.GET_ALL_PENDING_CUSTOMER_ACCOUNTS;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setBoolean(1, true);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Customer customer = new Customer(
					resultSet.getString("userName"),
					resultSet.getString("firstName"),
					resultSet.getString("lastName"),
					resultSet.getLong("contact")
				);
				pendingCustomersList.add(customer);
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException(BankDbUtilProps.ERROR_MESSAGE);
		}
		return pendingCustomersList;
	}

	@Override
	public List<Account> getAllPendingTransactionalAccounts() throws BusinessException {
		List<Account> pendingAccountsList = new ArrayList<>();
		try (Connection connection = BankPostgresSqlConnection.getConnection()) {
			String sql = BankDbQueries.GET_ALL_PENDING_TRANSACTIONAL_ACCOUNTS;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setBoolean(1, true);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Account account = new Account(
					resultSet.getString("userName"),
					resultSet.getInt("accountNumber"),
					resultSet.getLong("balance")
				);
				pendingAccountsList.add(account);
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException(BankDbUtilProps.ERROR_MESSAGE);
		}
		return pendingAccountsList;
	}

	@Override
	public List<Customer> getAllCustomerAccounts() throws BusinessException {
		List<Customer> customersList = new ArrayList<>();
		try (Connection connection = BankPostgresSqlConnection.getConnection()) {
			String sql = BankDbQueries.GET_ALL_CUSTOMER_ACCOUNTS;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setBoolean(1, false);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Customer customer = new Customer(
					resultSet.getString("userName"),
					resultSet.getString("firstName"),
					resultSet.getString("lastName"),
					resultSet.getLong("contact")
				);
				customersList.add(customer);
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException(BankDbUtilProps.ERROR_MESSAGE);
		}
		return customersList;
	}

}
