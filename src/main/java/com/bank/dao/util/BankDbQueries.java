package com.bank.dao.util;

public class BankDbQueries {
	
	public static final String VERIFY_CUSTOMER_LOGIN = "select password, isPending from bank.customer where userName=?";
	public static final String VERIFY_EMPLOYEE_LOGIN = "select password from bank.employee where employeeId=?";
	public static final String REGISTER_NEW_CUSTOMER_ACCOUNT = 
			"insert into bank.customer(userName, password, firstName, lastName, contact, isPending) " + 
			"Values(?,?,?,?,?,?)";
	public static final String CHECK_CUSTOMER_USERNAME_DUPLICATE = 
			"select customer.username from bank.customer where customer.username=?";
	public static final String CHECK_ACCOUNT_NUMBER_DUPLICATE = 
			"select account.accountNumber from bank.account where account.accountNumber=?";
	public static final String REGISTER_NEW_TRANSACTIONAL_ACCOUNT = 
			"insert into bank.account(accountNumber, balance, userName, isPending) " + 
			"Values(?,?,?,?)";
	public static final String GET_ALL_TRANSACTIONAL_ACCOUNTS = 
			"select accountNumber, balance, userName, isPending from bank.account where userName=?";
	public static final String GET_ACCOUNT = "select accountNumber, balance, userName from bank.account where accountNumber=?";
	public static final String UPDATE_ACCOUNT_BALANCE = "update bank.account set balance=? where accountNumber=?";
	public static final String CREATE_NEW_TRANSFER = "insert into bank.transfers(id, amount, accountNumber) Values(?,?,?)";
	public static final String CHECK_ID_DUPLICATE = "select transfers.id from bank.transfers where transfers.id=?";
	public static final String GET_ALL_TRANSFERS = "select id, amount, accountNumber from bank.transfers where accountNumber=?";
	public static final String GET_TRANSFER = "select id, amount, accountNumber from bank.transfers where id=?";	
	public static final String DELETE_TRANSFER = "delete from bank.transfers where id=?";
	public static final String GET_ALL_PENDING_CUSTOMER_ACCOUNTS = 
			"select userName, firstName, lastName, contact, isPending from bank.customer where isPending=?";
	public static final String APPROVE_CUSTOMER_ACCOUNT = "update bank.customer set isPending=? where userName=?";
	public static final String DELETE_CUSTOMER_ACCOUNT = "delete from bank.customer where userName=?";
	public static final String GET_ALL_PENDING_TRANSACTIONAL_ACCOUNTS = 
			"select accountNumber, balance, userName, isPending from bank.account where isPending=?";
	public static final String APPROVE_TRANSACTIONAL_ACCOUNT = "update bank.account set isPending=? where accountNumber=?";
	public static final String DELETE_TRANSACTIONAL_ACCOUNT = "delete from bank.account where accountNumber=?";
	public static final String GET_MAX_ID = "select max(id) as maxId from bank.log";
	public static final String ADD_TO_LOG = "insert into bank.log Values(?,?,?,?,?)";
	public static final String GET_ALL_CUSTOMER_ACCOUNTS = 
			"select userName, firstName, lastName, contact from bank.customer where isPending=?";
	public static final String GET_ACCOUNT_HISTORY = 
			"select id, type, amount, timestamp, accountNumber from bank.log where accountNumber=?";
	
}
