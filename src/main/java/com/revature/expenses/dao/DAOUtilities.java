package com.revature.expenses.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.revature.expenses.services.helpers.LoggerSingleton;

public class DAOUtilities {
	private static final String CONNECTION_USERNAME = System.getenv("REV_BANK_USR");
	private static final String CONNECTION_PASSWORD = System.getenv("REV_BANK_CONN");
	private static final String URL = System.getenv("REV_BANK_URL");
	
//	private static AccountDAOImpl accountDAOImpl;
	private static Connection connection;
//	
//	public static synchronized AccountDAOImpl getAccountDao() {
//		if(accountDAOImpl == null) {
//			accountDAOImpl = new AccountDAOImpl();
//		}
//		return accountDAOImpl;
//	}
	public static synchronized Connection getConnection() throws SQLException {
		try {
			if(CONNECTION_PASSWORD == null) {
				throw new RuntimeException("System env password 'REV_BANK_CONN' is not set, "
						+ "connecting without password is not possible.");
			}
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//This above statement uses Reflection to confirm that a class with this fully qualified name
			//is available
			try {
				connection = DriverManager.getConnection(URL,CONNECTION_USERNAME,CONNECTION_PASSWORD);
			}catch(SQLException e) {
				LoggerSingleton.getExceptionLogger().warn("Failed to get connection", e);
			}
		}catch(ClassNotFoundException e) {
			LoggerSingleton.getExceptionLogger().warn("Oracle db driver not found",e);
		}catch(RuntimeException e) {
			LoggerSingleton.getExceptionLogger().warn("Connection Failed", e);
		}
		if (connection.isClosed()){
			System.out.println("getting new connection...");
			connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		}
		return connection;
	}
	public static void commit(Connection conn) throws SQLException {
		String sql = "COMMIT";
		try(PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.execute();
		}
	}
	public static void rollback(Connection conn) throws SQLException {
		String sql = "ROLLBACK";
		try(PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.execute();
		}
	}
}
