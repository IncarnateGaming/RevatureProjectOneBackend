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
import com.revature.expenses.dao.interfaces.UserRoleDAO;
import com.revature.expenses.models.UserRole;

public class UserRoleDAOImpl implements UserRoleDAO {

	@Override
	public UserRole create(UserRole userRoleToCreate) {
		UserRole result = null;
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "call admin.create_ers_user_role(?,?)";
			try(CallableStatement stmt = conn.prepareCall(sql)){
				stmt.registerOutParameter(1, Types.INTEGER);
				stmt.setString(2, userRoleToCreate.getRole());
				stmt.execute();
				int resultId = (Integer) stmt.getObject(1);
				userRoleToCreate.setId(resultId);
				result = userRoleToCreate;
			}
			DAOUtilities.commit(conn);
		}catch(SQLException e) {
//			LoggerSingleton.getLogger().warn("Failed to create account",e);
		}
		return result;
	}

	@Override
	public List<UserRole> list() {
		List<UserRole> list = new ArrayList<>();
		try (Connection conn = DAOUtilities.getConnection()){
			try(Statement stmt = conn.createStatement()){
				String sql = "SELECT * FROM ADMIN.ERS_USER_ROLES";
				try(ResultSet rs = stmt.executeQuery(sql)){
					while(rs.next()) {
						UserRole obj = objectBuilder(rs);
						list.add(obj);
					}
				}
			}
		}catch(SQLException e) {
//			LoggerSingleton.getLogger().warn("Failed to get accounts",e);
		}
		return list;
	}

	@Override
	public UserRole get(int userRoleId) {
		UserRole result = null;
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "SELECT * FROM ADMIN.ERS_USER_ROLES "
					+ "WHERE ers_user_role_id = ?";
			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setInt(1, userRoleId);
				try(ResultSet rs = stmt.executeQuery(sql)){
					while(rs.next()) {
						result = objectBuilder(rs);
					}
				}
			}
		}catch(SQLException e) {
//			LoggerSingleton.getLogger().warn("Failed to get accounts",e);
		}
		return result;
	}

	@Override
	public UserRole update(UserRole userRoleToUpdate) {
		UserRole result = null;
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "UPDATE ADMIN.ERS_USER_ROLES "
				+ "SET user_role = ? "
				+ "WHERE ADMIN.ERS_USER_ROLES.ers_user_role_id = ?";

			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setString(1, userRoleToUpdate.getRole());
				stmt.setInt(2, userRoleToUpdate.getId());
				int rs = stmt.executeUpdate();
				if (rs > 0) {
					result = userRoleToUpdate;
				}
			}
		}catch(SQLException e) {
//			LoggerSingleton.getLogger().warn("Failed to get accounts",e);
		}
		return result;
	}

	@Override
	public boolean delete(UserRole userRoleToDelete) {
		boolean result = false;
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "DELETE ADMIN.ERS_USER_ROLES "
				+ "WHERE ADMIN.ERS_USER_ROLES.ers_user_role_id = ?";

			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setInt(1, userRoleToDelete.getId());
				int rs = stmt.executeUpdate();
				if (rs > 0) {
					result = true;
				}
			}
		}catch(SQLException e) {
//			LoggerSingleton.getLogger().warn("Failed to get accounts",e);
		}
		return result;
	}

	@Override
	public int getHighestId() {
		int result = 0;
		try (Connection conn = DAOUtilities.getConnection()){
			try(Statement stmt = conn.createStatement()){
				String sql = "SELECT MAX(ers_user_role_id) FROM ADMIN.ERS_USER_ROLES";
				try(ResultSet rs = stmt.executeQuery(sql)){
					result = rs.getInt(1);
				}
			}
		}catch(SQLException e) {
//			LoggerSingleton.getLogger().warn("Failed to get max account id",e);
		}
		return result;
	}
	private UserRole objectBuilder(ResultSet rs) throws SQLException {
		UserRole result = new UserRole(
				rs.getString("user_role")
				);
		result.setId(rs.getInt("ers_user_role_id"));
		return result;
	}
}
