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
				LoggerSingleton.getExceptionLogger().warn("Failed to get connection",e);
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
