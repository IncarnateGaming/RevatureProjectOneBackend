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
import com.revature.expenses.dao.interfaces.ReimbursmentTypeDAO;
import com.revature.expenses.models.ReimbursmentType;

public class ReimbursmentTypeDAOImpl implements ReimbursmentTypeDAO {

	@Override
	public ReimbursmentType create(ReimbursmentType reimbursmentTypeToCreate) {
		ReimbursmentType result = null;
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "call admin.create_ers_reimbursment_type(?,?)";
			try(CallableStatement stmt = conn.prepareCall(sql)){
				stmt.registerOutParameter(1, Types.INTEGER);
				stmt.setString(2, reimbursmentTypeToCreate.getType());
				stmt.execute();
				int resultId = (Integer) stmt.getObject(1);
				reimbursmentTypeToCreate.setId(resultId);
				result = reimbursmentTypeToCreate;
			}
			DAOUtilities.commit(conn);
		}catch(SQLException e) {
//			LoggerSingleton.getLogger().warn("Failed to create account",e);
		}
		return result;
	}

	@Override
	public List<ReimbursmentType> list() {
		List<ReimbursmentType> list = new ArrayList<>();
		try (Connection conn = DAOUtilities.getConnection()){
			try(Statement stmt = conn.createStatement()){
				String sql = "SELECT * FROM ADMIN.ERS_REIMBURSEMENT_TYPE";
				try(ResultSet rs = stmt.executeQuery(sql)){
					while(rs.next()) {
						ReimbursmentType obj = objectBuilder(rs);
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
	public ReimbursmentType get(int reimbursmentTypeId) {
		ReimbursmentType result = null;
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "SELECT * FROM ADMIN.ERS_REIMBURSEMENT_TYPE "
					+ "WHERE reimb_type_id = ?";
			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setInt(1, reimbursmentTypeId);
				try(ResultSet rs = stmt.executeQuery()){
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
	public ReimbursmentType get(String type) {
		ReimbursmentType result = null;
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "SELECT * FROM ADMIN.ERS_REIMBURSEMENT_TYPE "
					+ "WHERE reimb_type = ?";
			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setString(1, type);
				try(ResultSet rs = stmt.executeQuery()){
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
	public ReimbursmentType update(ReimbursmentType reimbursmentTypeToUpdate) {
		ReimbursmentType result = null;
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "UPDATE ADMIN.ERS_REIMBURSEMENT_TYPE "
				+ "SET reimb_type = ? "
				+ "WHERE ADMIN.ERS_REIMBURSEMENT_TYPE.reimb_type_id = ?";

			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setString(1, reimbursmentTypeToUpdate.getType());
				stmt.setInt(2, reimbursmentTypeToUpdate.getId());
				int rs = stmt.executeUpdate();
				if (rs > 0) {
					result = reimbursmentTypeToUpdate;
				}
			}
		}catch(SQLException e) {
//			LoggerSingleton.getLogger().warn("Failed to get accounts",e);
		}
		return result;
	}

	@Override
	public boolean delete(ReimbursmentType reimbursmentTypeToDelete) {
		boolean result = false;
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "DELETE ADMIN.ERS_REIMBURSEMENT_TYPE "
				+ "WHERE ADMIN.ERS_REIMBURSEMENT_TYPE.reimb_type_id = ?";

			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setInt(1, reimbursmentTypeToDelete.getId());
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
				String sql = "SELECT MAX(reimb_type_id) FROM ADMIN.ERS_REIMBURSEMENT_TYPE";
				try(ResultSet rs = stmt.executeQuery(sql)){
					result = rs.getInt(1);
				}
			}
		}catch(SQLException e) {
//			LoggerSingleton.getLogger().warn("Failed to get max account id",e);
		}
		return result;
	}
	private ReimbursmentType objectBuilder(ResultSet rs) throws SQLException {
		ReimbursmentType result = new ReimbursmentType(
				rs.getString("reimb_type")
				);
		result.setId(rs.getInt("reimb_type_id"));
		return result;
	}
}
