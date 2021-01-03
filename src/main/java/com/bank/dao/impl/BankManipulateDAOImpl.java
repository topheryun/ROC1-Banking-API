package com.bank.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.bank.dao.BankManipulateDAO;
import com.bank.dao.util.BankDbQueries;
import com.bank.dao.util.BankDbUtilProps;
import com.bank.dao.util.BankPostgresSqlConnection;
import com.bank.exception.BusinessException;
import com.bank.model.Account;
import com.bank.model.Customer;

public class BankManipulateDAOImpl implements BankManipulateDAO {
	
	private static Logger log = Logger.getLogger(BankManipulateDAOImpl.class);

	@Override
	public boolean createNewTransactionalAccount(Account account) throws BusinessException {
		boolean isCreated = false;
		try (Connection connection = BankPostgresSqlConnection.getConnection()) {
			String sql = BankDbQueries.REGISTER_NEW_TRANSACTIONAL_ACCOUNT;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, account.getAccountNumber());
			preparedStatement.setFloat(2, account.getBalance());
			preparedStatement.setString(3, account.getUserName());
			preparedStatement.setBoolean(4, true);
			
			preparedStatement.executeUpdate();
			isCreated = true;
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException(BankDbUtilProps.ERROR_MESSAGE);
		}
		return isCreated;
	}
	
	@Override
	public boolean registerNewCustomerAccount(Customer customer, String password) throws BusinessException {
		boolean isRegistered = false;
		try (Connection connection = BankPostgresSqlConnection.getConnection()) {
			String sql = BankDbQueries.CHECK_CUSTOMER_USERNAME_DUPLICATE;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, customer.getUserName());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				log.warn("Username already exists.");
			}
			else {
				sql = BankDbQueries.REGISTER_NEW_CUSTOMER_ACCOUNT;
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, customer.getUserName());
				preparedStatement.setString(2, password);
				preparedStatement.setString(3, customer.getFirstName());
				preparedStatement.setString(4, customer.getLastName());
				preparedStatement.setLong(5, customer.getContact());
				preparedStatement.setBoolean(6, true);
				preparedStatement.executeUpdate();
				isRegistered = true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException(BankDbUtilProps.ERROR_MESSAGE);
		}
		return isRegistered;
	}

	@Override
	public boolean updateAccount(Account account, float amount) throws BusinessException {
		boolean isUpdated = false;
		try (Connection connection = BankPostgresSqlConnection.getConnection()) {
			String sql = BankDbQueries.UPDATE_ACCOUNT_BALANCE;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setFloat(1, amount);
			preparedStatement.setInt(2, account.getAccountNumber());
			preparedStatement.executeUpdate();
			isUpdated = true;
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException(BankDbUtilProps.ERROR_MESSAGE);
		}
		return isUpdated;
	}

	@Override
	public boolean createTransfer(int id, Account targetAccount, float amount) throws BusinessException {
		boolean isCreated = false;
		try (Connection connection = BankPostgresSqlConnection.getConnection()) {
			String sql = BankDbQueries.CREATE_NEW_TRANSFER;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			preparedStatement.setFloat(2, amount);
			preparedStatement.setInt(3, targetAccount.getAccountNumber());
			preparedStatement.executeUpdate();
			isCreated = true;
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException(BankDbUtilProps.ERROR_MESSAGE);
		}
		return isCreated;
	}

	@Override
	public boolean deleteTransfer(Account transfer) throws BusinessException {
		boolean isDeleted = false;
		try (Connection connection = BankPostgresSqlConnection.getConnection()) {
			String sql = BankDbQueries.DELETE_TRANSFER;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.parseInt(transfer.getUserName()));
			preparedStatement.executeUpdate();
			isDeleted = true;
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException(BankDbUtilProps.ERROR_MESSAGE);
		}
		return isDeleted;
	}

	@Override
	public boolean approveCustomerAccount(Customer customer) throws BusinessException {
		boolean isApproved = false;
		try (Connection connection = BankPostgresSqlConnection.getConnection()) {
			String sql = BankDbQueries.APPROVE_CUSTOMER_ACCOUNT;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setBoolean(1, false);
			preparedStatement.setString(2, customer.getUserName());
			preparedStatement.executeUpdate();
			isApproved = true;
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException(BankDbUtilProps.ERROR_MESSAGE);
		}
		return isApproved;
	}

	@Override
	public boolean deleteCustomerAccount(Customer customer) throws BusinessException {
		boolean isDeleted = false;
		try (Connection connection = BankPostgresSqlConnection.getConnection()) {
			String sql = BankDbQueries.DELETE_CUSTOMER_ACCOUNT;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, customer.getUserName());
			preparedStatement.executeUpdate();
			isDeleted = true;
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException(BankDbUtilProps.ERROR_MESSAGE);
		}
		return isDeleted;
	}

	@Override
	public boolean approveTransactionalAccount(Account account) throws BusinessException {
		boolean isApproved = false;
		try (Connection connection = BankPostgresSqlConnection.getConnection()) {
			String sql = BankDbQueries.APPROVE_TRANSACTIONAL_ACCOUNT;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setBoolean(1, false);
			preparedStatement.setInt(2, account.getAccountNumber());
			preparedStatement.executeUpdate();
			isApproved = true;
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException(BankDbUtilProps.ERROR_MESSAGE);
		}
		return isApproved;
	}

	@Override
	public boolean deleteTransactionalAccount(Account account) throws BusinessException {
		boolean isDeleted = false;
		try (Connection connection = BankPostgresSqlConnection.getConnection()) {
			String sql = BankDbQueries.DELETE_TRANSACTIONAL_ACCOUNT;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, account.getAccountNumber());
			preparedStatement.executeUpdate();
			isDeleted = true;
		} catch (ClassNotFoundException | SQLException e) {
			log.error(e);
			throw new BusinessException(BankDbUtilProps.ERROR_MESSAGE);
		}
		return isDeleted;
	}

}
