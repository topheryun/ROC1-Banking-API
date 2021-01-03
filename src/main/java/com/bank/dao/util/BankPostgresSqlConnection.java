package com.bank.dao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BankPostgresSqlConnection {
	
	private static Connection connection;
	
	private BankPostgresSqlConnection() {
	}
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(BankDbUtilProps.DRIVER);
		String url = BankDbUtilProps.URL;
		String username = BankDbUtilProps.USERNAME;
		String password = BankDbUtilProps.PASSWORD;
		connection = DriverManager.getConnection(url, username, password);
		return connection;
	}

}
