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
import com.revature.expenses.dao.interfaces.ReimbursmentStatusDAO;
import com.revature.expenses.models.ReimbursmentStatus;

public class ReimbursmentStatusDAOImpl implements ReimbursmentStatusDAO {

	@Override
	public ReimbursmentStatus create(ReimbursmentStatus reimbursmentStatusToCreate) {
		ReimbursmentStatus result = null;
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "call admin.create_ers_reimbursment_status(?,?)";
			try(CallableStatement stmt = conn.prepareCall(sql)){
				stmt.registerOutParameter(1, Types.INTEGER);
				stmt.setString(2, reimbursmentStatusToCreate.getStatus());
				stmt.execute();
				int resultId = (Integer) stmt.getObject(1);
				reimbursmentStatusToCreate.setId(resultId);
				result = reimbursmentStatusToCreate;
			}
			DAOUtilities.commit(conn);
		}catch(SQLException e) {
//			LoggerSingleton.getLogger().warn("Failed to create account",e);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<ReimbursmentStatus> list() {
		List<ReimbursmentStatus> list = new ArrayList<>();
		try (Connection conn = DAOUtilities.getConnection()){
			try(Statement stmt = conn.createStatement()){
				String sql = "SELECT * FROM ADMIN.ERS_REIMBURSMENT_STATUS";
				try(ResultSet rs = stmt.executeQuery(sql)){
					while(rs.next()) {
						ReimbursmentStatus obj = objectBuilder(rs);
						list.add(obj);
					}
				}
			}
		}catch(SQLException e) {
//			LoggerSingleton.getLogger().warn("Failed to get accounts",e);
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ReimbursmentStatus get(int reimbursmentStatusId) {
		ReimbursmentStatus result = null;
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "SELECT * FROM ADMIN.ERS_REIMBURSMENT_STATUS WHERE reimb_status_id = ?";
			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setInt(1, reimbursmentStatusId);
				try(ResultSet rs = stmt.executeQuery()){
					while(rs.next()) {
						result = objectBuilder(rs);
					}
				}
			}
		}catch(SQLException e) {
//			LoggerSingleton.getLogger().warn("Failed to get accounts",e);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ReimbursmentStatus get(String status) {
		ReimbursmentStatus result = null;
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "SELECT * FROM ADMIN.ERS_REIMBURSMENT_STATUS WHERE reimb_status = ?";
			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setString(1, status);
				try(ResultSet rs = stmt.executeQuery()){
					while(rs.next()) {
						result = objectBuilder(rs);
					}
				}
			}
		}catch(SQLException e) {
//			LoggerSingleton.getLogger().warn("Failed to get accounts",e);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ReimbursmentStatus update(ReimbursmentStatus reimbursmentStatusToUpdate) {
		ReimbursmentStatus result = null;
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "UPDATE ADMIN.ERS_REIMBURSMENT_STATUS "
				+ "SET reimb_status = ? "
				+ "WHERE ADMIN.ERS_REIMBURSMENT_STATUS.reimb_status_id = ?";

			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setString(1, reimbursmentStatusToUpdate.getStatus());
				stmt.setInt(2, reimbursmentStatusToUpdate.getId());
				int rs = stmt.executeUpdate();
				if (rs > 0) {
					result = reimbursmentStatusToUpdate;
				}
			}
		}catch(SQLException e) {
//			LoggerSingleton.getLogger().warn("Failed to get accounts",e);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean delete(ReimbursmentStatus reimbursmentStatusToDelete) {
		boolean result = false;
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "DELETE ADMIN.ERS_REIMBURSMENT_STATUS "
				+ "WHERE ADMIN.ERS_REIMBURSMENT_STATUS.reimb_status_id = ?";
			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setInt(1, reimbursmentStatusToDelete.getId());
				int rs = stmt.executeUpdate();
				if (rs > 0) {
					result = true;
				}
			}
		}catch(SQLException e) {
//			LoggerSingleton.getLogger().warn("Failed to get accounts",e);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int getHighestId() {
		int result = 0;
		try (Connection conn = DAOUtilities.getConnection()){
			try(Statement stmt = conn.createStatement()){
				String sql = "SELECT MAX(reimb_status_id) FROM ADMIN.ERS_REIMBURSMENT_STATUS";
				try(ResultSet rs = stmt.executeQuery(sql)){
					result = rs.getInt(1);
				}
			}
		}catch(SQLException e) {
//			LoggerSingleton.getLogger().warn("Failed to get max account id",e);
			e.printStackTrace();
		}
		return result;
	}
	private ReimbursmentStatus objectBuilder(ResultSet rs) throws SQLException {
		ReimbursmentStatus result = new ReimbursmentStatus(
				rs.getString("reimb_status")
				);
		result.setId(rs.getInt("reimb_status_id"));
		return result;
	}
}
