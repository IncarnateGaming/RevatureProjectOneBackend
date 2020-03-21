package com.revature.expenses.dao.implementations;

import java.awt.image.BufferedImage;
import java.sql.Blob;
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
import com.revature.expenses.dao.interfaces.ReimbursmentDAO;
import com.revature.expenses.models.Reimbursment;
import com.revature.expenses.models.User;
import com.revature.expenses.services.handlers.ReimbursmentStatusHandler;
import com.revature.expenses.services.handlers.ReimbursmentTypeHandler;
import com.revature.expenses.services.handlers.UserHandler;
import com.revature.expenses.services.handlers.UserRoleHandler;
import com.revature.expenses.services.helpers.LoggerSingleton;

public class ReimbursmentDAOImpl implements ReimbursmentDAO {

	private static ReimbursmentStatusHandler reimbursmentStatusHandler;
	private static ReimbursmentTypeHandler reimbursmentTypeHandler;
	private static UserHandler userHandler;
	private static UserRoleHandler userRoleHandler;
	public static ReimbursmentStatusHandler getReimbursmentStatusHandler() {
		if(reimbursmentStatusHandler == null) {
			reimbursmentStatusHandler = new ReimbursmentStatusHandler();
		}
		return reimbursmentStatusHandler;
	}
	public static UserHandler getUserHandler() {
		if(userHandler == null) {
			userHandler = new UserHandler();
		}
		return userHandler;
	}
	public static ReimbursmentTypeHandler getReimbursmentTypeHandler() {
		if(reimbursmentTypeHandler == null) {
			reimbursmentTypeHandler = new ReimbursmentTypeHandler();
		}
		return reimbursmentTypeHandler;
	}
	public static UserRoleHandler getUserRoleHandler() {
		if(userRoleHandler == null) {
			userRoleHandler = new UserRoleHandler();
		}
		return userRoleHandler;
	}
	@Override
	public Reimbursment create(Reimbursment reimbursmentToCreate) {
		Reimbursment result = null;
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "call admin.create_ers_reimbursement(?,?,?,?,?,?,?)";
			try(CallableStatement stmt = conn.prepareCall(sql)){
				stmt.registerOutParameter(1, Types.INTEGER);
				stmt.setDouble(2, reimbursmentToCreate.getAmount());
				stmt.setString(3, reimbursmentToCreate.getDescription());
				stmt.setBlob(4, (Blob) reimbursmentToCreate.getReceipt());
				stmt.setInt(5, reimbursmentToCreate.getAuthor().getId());
				stmt.setInt(6, reimbursmentToCreate.getStatus().getId());
				stmt.setInt(7, reimbursmentToCreate.getType().getId());
				stmt.execute();
				int resultId = (Integer) stmt.getObject(1);
				reimbursmentToCreate.setId(resultId);
				result = reimbursmentToCreate;
			}
			DAOUtilities.commit(conn);
		}catch(SQLException e) {
			e.printStackTrace();
			LoggerSingleton.getExceptionLogger().warn("Failed to create account",e);
		}
		return result;
	}

	@Override
	public List<Reimbursment> list() {
		List<Reimbursment> list = new ArrayList<>();
		try (Connection conn = DAOUtilities.getConnection()){
			try(Statement stmt = conn.createStatement()){
				String sql = "SELECT * FROM ADMIN.ERS_REIMBURSEMENT";
				try(ResultSet rs = stmt.executeQuery(sql)){
					while(rs.next()) {
						Reimbursment obj = objectBuilder(rs);
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
	public List<Reimbursment> list(User user) {
		List<Reimbursment> list = new ArrayList<>();
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "SELECT * FROM ADMIN.ERS_REIMBURSEMENT "
					+ "WHERE reimb_author = ? ";
			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setInt(1, user.getId());
				try(ResultSet rs = stmt.executeQuery()){
					while(rs.next()) {
						Reimbursment obj = objectBuilder(rs);
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
	public List<Reimbursment> list(int limit, int offset) {
		List<Reimbursment> list = new ArrayList<>();
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "SELECT * FROM ADMIN.ERS_REIMBURSEMENT "
					+ "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setInt(1, offset);
				stmt.setInt(2, limit);
				try(ResultSet rs = stmt.executeQuery()){
					while(rs.next()) {
						Reimbursment obj = objectBuilder(rs);
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
	public List<Reimbursment> list(User user, int limit, int offset) {
		List<Reimbursment> list = new ArrayList<>();
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "SELECT * FROM ADMIN.ERS_REIMBURSEMENT "
					+ "WHERE reimb_author = ? "
					+ "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setInt(1, user.getId());
				stmt.setInt(2, offset);
				stmt.setInt(3, limit);
				try(ResultSet rs = stmt.executeQuery()){
					while(rs.next()) {
						Reimbursment obj = objectBuilder(rs);
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
	public List<Reimbursment> list(int limit, int offset, int status) {
		List<Reimbursment> list = new ArrayList<>();
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "SELECT * FROM ADMIN.ERS_REIMBURSEMENT "
					+ "WHERE reimb_status_id = ? "
					+ "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setInt(1, status);
				stmt.setInt(2, offset);
				stmt.setInt(3, limit);
				try(ResultSet rs = stmt.executeQuery()){
					while(rs.next()) {
						Reimbursment obj = objectBuilder(rs);
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
	public List<Reimbursment> list(User user, int limit, int offset, int status) {
		List<Reimbursment> list = new ArrayList<>();
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "SELECT * FROM ADMIN.ERS_REIMBURSEMENT "
					+ "WHERE reimb_status_id = ? AND reimb_author = ? "
					+ "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setInt(1, status);
				stmt.setInt(2, user.getId());
				stmt.setInt(3, offset);
				stmt.setInt(4, limit);
				try(ResultSet rs = stmt.executeQuery()){
					while(rs.next()) {
						Reimbursment obj = objectBuilder(rs);
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
	public Reimbursment get(int reimbursmentId) {
		Reimbursment result = null;
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "SELECT * FROM ADMIN.ERS_REIMBURSEMENT "
					+ "WHERE reimb_id = ?";
			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setInt(1, reimbursmentId);
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
	public Reimbursment update(Reimbursment reimbursmentToUpdate) {
		Reimbursment result = null;
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "UPDATE ADMIN.ERS_REIMBURSEMENT SET "
				+ "reimb_resolved = ?, reimb_description = ?, "
				+ "reimb_receipt = ?, reimb_resolver = ?, reimb_status_id = ?, "
				+ "reimb_type_id = ? "
				+ "WHERE reimb_id = ?";

			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setDate(1, reimbursmentToUpdate.getResolved());
				stmt.setString(2, reimbursmentToUpdate.getDescription());
				stmt.setBlob(3, (Blob) reimbursmentToUpdate.getReceipt());
				stmt.setInt(4, reimbursmentToUpdate.getResolver().getId());
				stmt.setInt(5, reimbursmentToUpdate.getStatus().getId());
				stmt.setInt(6, reimbursmentToUpdate.getType().getId());
				stmt.setInt(7, reimbursmentToUpdate.getId());
				int rs = stmt.executeUpdate();
				if (rs > 0) {
					result = reimbursmentToUpdate;
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
//			LoggerSingleton.getLogger().warn("Failed to get accounts",e);
		}
		return result;
	}

	@Override
	public boolean delete(Reimbursment reimbursmentToDelete) {
		boolean result = false;
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "DELETE ADMIN.ERS_REIMBURSEMENT "
				+ "WHERE ADMIN.ERS_REIMBURSEMENT.reimb_id = ?";
			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setInt(1, reimbursmentToDelete.getId());
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
				String sql = "SELECT MAX(reimb_id) FROM ADMIN.ERS_REIMBURSEMENT";
				try(ResultSet rs = stmt.executeQuery(sql)){
					result = rs.getInt(1);
				}
			}
		}catch(SQLException e) {
//			LoggerSingleton.getLogger().warn("Failed to get max account id",e);
		}
		return result;
	}
	private Reimbursment objectBuilder(ResultSet rs) throws SQLException {
		Reimbursment result =  new Reimbursment(
				rs.getDouble("reimb_amount"),
				getUserHandler().get(rs.getInt("reimb_author")),
				getReimbursmentStatusHandler().get(rs.getInt("reimb_status_id")),
				getReimbursmentTypeHandler().get(rs.getInt("reimb_type_id"))
				);
		result.setSubmitted(rs.getDate("reimb_submitted"));
		result.setDescription(rs.getString("reimb_description"));
		result.setId(rs.getInt("reimb_id"));
		result.setReceipt((BufferedImage) rs.getBlob("reimb_receipt"));
		result.setResolved(rs.getDate("reimb_resolved"));
		result.setResolver(getUserHandler().get(rs.getInt("reimb_resolver")));
		return result;
	}
}
