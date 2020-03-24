package com.revature.expenses.dao.implementations;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.revature.expenses.dao.DAOUtilities;
import com.revature.expenses.dao.interfaces.UserDAO;
import com.revature.expenses.models.User;
import com.revature.expenses.services.handlers.UserRoleHandler;
import com.revature.expenses.services.helpers.LoggerSingleton;

public class UserDAOImpl implements UserDAO {

	private static UserRoleHandler userRoleHandler;
	private UserRoleHandler getUserRoleHandler() {
		if(userRoleHandler == null) {
			userRoleHandler = new UserRoleHandler();
		}
		return userRoleHandler;
	}
	@Override
	public User create(User userToCreate) {
		User result = null;
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "call admin.create_ers_user(?,?,?,?)";
			try(CallableStatement stmt = conn.prepareCall(sql)){
				stmt.registerOutParameter(1, Types.INTEGER);
				stmt.setString(2, userToCreate.getUsername());
				stmt.setString(3, userToCreate.getPassword());
				stmt.setString(4, userToCreate.getEmail());
				stmt.execute();
				int resultId = (Integer) stmt.getObject(1);
				userToCreate.setId(resultId);
				result = userToCreate;
			}
			DAOUtilities.commit(conn);
		}catch(SQLException e) {
			LoggerSingleton.getExceptionLogger().warn("Failed to create user",e);
		}
		return result;
	}

	@Override
	public List<User> list() {
		List<User> list = new ArrayList<>();
		try (Connection conn = DAOUtilities.getConnection()){
			try(Statement stmt = conn.createStatement()){
				String sql = "SELECT * FROM ADMIN.ERS_USERS";
				try(ResultSet rs = stmt.executeQuery(sql)){
					while(rs.next()) {
						User obj = objectBuilder(rs);
						obj.setPassword("****");
						list.add(obj);
					}
				}
			}
		}catch(SQLException e) {
			LoggerSingleton.getExceptionLogger().warn("Failed to list users",e);
		}
		return list;
	}

	@Override
	public User get(int userId) {
		User result = null;
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "SELECT * FROM ADMIN.ERS_USERS "
					+ "WHERE ers_users_id = ?";
			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setInt(1, userId);
				try(ResultSet rs = stmt.executeQuery()){
					while(rs.next()) {
						result = objectBuilder(rs);
					}
				}
			}
		}catch(SQLException e) {
			LoggerSingleton.getExceptionLogger().warn("Failed to get user",e);
		}
		return result;
	}

	@Override
	public User get(String username) {
		User result = null;
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "SELECT * FROM ADMIN.ERS_USERS WHERE ers_username = ?";
			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setString(1, username);
				try(ResultSet rs = stmt.executeQuery()){
					while(rs.next()) {
						result = objectBuilder(rs);
					}
				}
			}
		}catch(SQLException e) {
			LoggerSingleton.getExceptionLogger().warn("Failed to get user",e);
		}
		return result;
	}

	@Override
	public User update(User userToUpdate) {
		User result = null;
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "UPDATE ADMIN.ERS_USERS "
				+ "SET ers_username = ?, user_first_name = ?, "
				+ "user_last_name = ?, user_email = ?, user_role_id = ? "
				+ "WHERE ers_users_id = ? ";

			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setString(1, userToUpdate.getUsername());
				stmt.setString(2, userToUpdate.getFirstName());
				stmt.setString(3, userToUpdate.getLastName());
				stmt.setString(4, userToUpdate.getEmail());
				if(userToUpdate.getRole() == null) {
					stmt.setNull(5, Types.INTEGER);
				}else {
					stmt.setInt(5, userToUpdate.getRole().getId());
				}
				stmt.setInt(6, userToUpdate.getId());
				int rs = stmt.executeUpdate();
				if (rs > 0) {
					result = userToUpdate;
				}
			}
		}catch(SQLException e) {
			LoggerSingleton.getExceptionLogger().warn("Failed to update user",e);
		}
		return result;
	}

	@Override
	public boolean delete(User userToDelete) {
		boolean result = false;
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "DELETE ADMIN.ERS_USERS "
				+ "WHERE ADMIN.ERS_USERS.ers_users_id = ?";
			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setInt(1, userToDelete.getId());
				int rs = stmt.executeUpdate();
				if (rs > 0) {
					result = true;
				}
			}
		}catch(SQLException e) {
			LoggerSingleton.getExceptionLogger().warn("Failed to delete user",e);
		}
		return result;
	}

	@Override
	public int getHighestId() {
		int result = 0;
		try (Connection conn = DAOUtilities.getConnection()){
			try(Statement stmt = conn.createStatement()){
				String sql = "SELECT MAX(ers_users_id) FROM ADMIN.ERS_USERS";
				try(ResultSet rs = stmt.executeQuery(sql)){
					result = rs.getInt(1);
				}
			}
		}catch(SQLException e) {
			LoggerSingleton.getExceptionLogger().warn("Failed to get highest user id",e);
		}
		return result;
	}
	private User objectBuilder(ResultSet rs) throws SQLException {
		User result = new User(
				rs.getString("ers_username"),
				rs.getString("ers_password"),
				rs.getString("user_email")
				);
		result.setId(rs.getInt("ers_users_id"));
		result.setFirstName(rs.getString("user_first_name"));
		result.setLastName(rs.getString("user_last_name"));
		result.setRole(getUserRoleHandler().get(rs.getInt("user_role_id")));
		return result;
	}
}
