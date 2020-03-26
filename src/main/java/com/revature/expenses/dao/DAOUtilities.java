package com.revature.expenses.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.revature.expenses.dao.implementations.ReimbursmentDAOImpl;
import com.revature.expenses.dao.implementations.ReimbursmentStatusDAOImpl;
import com.revature.expenses.dao.implementations.ReimbursmentTypeDAOImpl;
import com.revature.expenses.dao.implementations.UserDAOImpl;
import com.revature.expenses.dao.implementations.UserRoleDAOImpl;
import com.revature.expenses.dao.interfaces.ReimbursmentDAO;
import com.revature.expenses.dao.interfaces.ReimbursmentStatusDAO;
import com.revature.expenses.dao.interfaces.ReimbursmentTypeDAO;
import com.revature.expenses.dao.interfaces.UserDAO;
import com.revature.expenses.dao.interfaces.UserRoleDAO;
import com.revature.expenses.exceptions.ConnectionToDatabaseFailed;
import com.revature.expenses.services.helpers.LoggerSingleton;

//import com.revature.expenses.services.helpers.LoggerSingleton;

public class DAOUtilities {
	private static final String CONNECTION_USERNAME = System.getenv("REV_ERS_CONN_USR");
	private static final String CONNECTION_PASSWORD = System.getenv("REV_ERS_CONN_PASS");
	private static final String URL = System.getenv("REV_ERS_URL");
	
	private static ReimbursmentDAO reimbursmentDao;
	private static ReimbursmentStatusDAO reimbursmentStatusDao;
	private static ReimbursmentTypeDAO reimbursmentTypeDao;
	private static UserDAO userDao;
	private static UserRoleDAO userRoleDao;
	private static Connection connection;

	public static ReimbursmentDAO getReimbursmentDao() {
		if(reimbursmentDao == null) {
			reimbursmentDao = new ReimbursmentDAOImpl();
		}
		return reimbursmentDao;
	}
	public static ReimbursmentStatusDAO getReimbursmentStatusDao() {
		if(reimbursmentStatusDao == null) {
			reimbursmentStatusDao = new ReimbursmentStatusDAOImpl();
		}
		return reimbursmentStatusDao;
	}
	public static ReimbursmentTypeDAO getReimbursmentTypeDao() {
		if(reimbursmentTypeDao == null) {
			reimbursmentTypeDao = new ReimbursmentTypeDAOImpl();
		}
		return reimbursmentTypeDao;
	}
	public static UserDAO getUserDao() {
		if(userDao == null) {
			userDao = new UserDAOImpl();
		}
		return userDao;
	}
	public static UserRoleDAO getUserRoleDao() {
		if(userRoleDao == null) {
			userRoleDao = new UserRoleDAOImpl();
		}
		return userRoleDao;
	}
	public static synchronized Connection getConnection() throws SQLException {
		if(CONNECTION_PASSWORD == null) {
			LoggerSingleton.getExceptionLogger().warn("System env password 'REV_ERS_CONN_PASS' is not set, "
					+ "connecting without password is not possible.");
		}else if(CONNECTION_USERNAME == null) {
			LoggerSingleton.getExceptionLogger().warn("System env password 'REV_ERS_CONN_USR' is not set, "
					+ "connecting without username is not possible.");
		}else if(URL == null) {
			LoggerSingleton.getExceptionLogger().warn("System env password 'REV_ERS_URL' is not set, "
					+ "connecting without database location is not possible.");
		}
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//This above statement uses Reflection to confirm that a class with this fully qualified name
			//is available
			try {
				connection = DriverManager.getConnection(URL,CONNECTION_USERNAME,CONNECTION_PASSWORD);
			}catch(SQLException e) {
				LoggerSingleton.getExceptionLogger().warn("Failed to get connection",e);
			}
		}catch(ClassNotFoundException e) {
			LoggerSingleton.getExceptionLogger().warn("Oracle db driver not found",e);
		}catch(Exception e) {
			LoggerSingleton.getExceptionLogger().warn("Connection Failed", e);
		}
		if (connection == null) {
			LoggerSingleton.getExceptionLogger().fatal("Connection to database failed. Check internet status and credentials.");
			throw new ConnectionToDatabaseFailed();
		}else if (connection.isClosed()){
			System.out.println("reopening connection...");
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
