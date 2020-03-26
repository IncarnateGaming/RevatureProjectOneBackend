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

import javax.sql.rowset.serial.SerialBlob;

import com.revature.expenses.dao.DAOUtilities;
import com.revature.expenses.dao.interfaces.ReimbursmentDAO;
import com.revature.expenses.models.Reimbursment;
import com.revature.expenses.models.ReimbursmentStatus;
import com.revature.expenses.models.ReimbursmentType;
import com.revature.expenses.models.User;
import com.revature.expenses.models.UserRole;
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
	private static String selectJoin = ""
					+"SELECT "
					+ "main.reimb_id, main.reimb_amount, main.reimb_submitted, main.reimb_resolved, "
					+ "main.reimb_description, main.reimb_author, main.reimb_resolver, main.reimb_status_id, "
					+ "main.reimb_type_id, "
					+ "join_author.ers_users_id, join_author.ers_username, "
					+ "join_author.ers_password, join_author.user_first_name, join_author.user_last_name, "
					+ "join_author.user_email, join_author.user_role_id, join_author_role.ers_user_role_id, "
					+ "join_author_role.user_role, "	
					+ "join_resolver.ers_users_id, join_resolver.ers_username, "
					+ "join_resolver.ers_password, join_resolver.user_first_name, join_resolver.user_last_name, "
					+ "join_resolver.user_email, join_resolver.user_role_id, join_resolver_role.ers_user_role_id, "
					+ "join_resolver_role.user_role, "
					+ "join_status.reimb_status_id, join_status.reimb_status, "
					+ "join_type.reimb_type_id, join_type.reimb_type "
					+ "FROM ADMIN.ERS_REIMBURSEMENT main "
			;
	private static String joinTables =  ""
					+" LEFT JOIN ADMIN.ERS_USERS join_author ON "
						+ "main.reimb_author = join_author.ers_users_id "
					+" LEFT JOIN ADMIN.ERS_USER_ROLES join_author_role ON "
						+ "join_author.user_role_id = join_author_role.ers_user_role_id "
					+" LEFT JOIN ADMIN.ERS_USERS join_resolver ON "
						+ "main.reimb_resolver = join_resolver.ers_users_id "
					+" LEFT JOIN ADMIN.ERS_USER_ROLES join_resolver_role ON "
						+ "join_resolver.user_role_id = join_resolver_role.ers_user_role_id "
					+" LEFT JOIN ADMIN.ERS_REIMBURSMENT_STATUS join_status ON "
						+ "main.reimb_status_id = join_status.reimb_status_id "
					+" LEFT JOIN ADMIN.ERS_REIMBURSEMENT_TYPE join_type ON "
						+ "main.reimb_type_id = join_type.reimb_type_id "
			;
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
			String sql = "call admin.create_ers_reimbursement(?,?,?,?,?,?)";
			try(CallableStatement stmt = conn.prepareCall(sql)){
				stmt.registerOutParameter(1, Types.INTEGER);
				stmt.setDouble(2, reimbursmentToCreate.getAmount());
				stmt.setString(3, reimbursmentToCreate.getDescription());
				stmt.setInt(4, reimbursmentToCreate.getAuthor().getId());
				stmt.setInt(5, reimbursmentToCreate.getStatus().getId());
				stmt.setInt(6, reimbursmentToCreate.getType().getId());
				stmt.execute();
				int resultId = (Integer) stmt.getObject(1);
				reimbursmentToCreate.setId(resultId);
				result = reimbursmentToCreate;
			}
			DAOUtilities.commit(conn);
		}catch(SQLException e) {
			LoggerSingleton.getExceptionLogger().warn("Failed to create reimbursment",e);
		}
		return result;
	}

	@Override
	public List<Reimbursment> list() {
		List<Reimbursment> list = new ArrayList<>();
		try (Connection conn = DAOUtilities.getConnection()){
			try(Statement stmt = conn.createStatement()){
				String sql = selectJoin
					+ joinTables;
				try(ResultSet rs = stmt.executeQuery(sql)){
					while(rs.next()) {
						Reimbursment obj = objectBuilderJoin(rs);
						list.add(obj);
					}
				}
			}
		}catch(SQLException e) {
			LoggerSingleton.getExceptionLogger().warn("Failed to get reimbursments method 1: ",e);
		}
		return list;
	}
	@Override
	public List<Reimbursment> list(User user) {
		List<Reimbursment> list = new ArrayList<>();
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = selectJoin
					+ joinTables
					+ "WHERE main.reimb_author = ? ";
			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setInt(1, user.getId());
				try(ResultSet rs = stmt.executeQuery()){
					while(rs.next()) {
						Reimbursment obj = objectBuilderJoin(rs);
						list.add(obj);
					}
				}
			}
		}catch(SQLException e) {
			LoggerSingleton.getExceptionLogger().warn("Failed to get reimbursments method 2:",e);
		}
		return list;
	}
	@Override
	public List<Reimbursment> list(int limit, int offset) {
		List<Reimbursment> list = new ArrayList<>();
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = selectJoin
					+ joinTables
					+ "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setInt(1, offset);
				stmt.setInt(2, limit);
				try(ResultSet rs = stmt.executeQuery()){
					while(rs.next()) {
						Reimbursment obj = objectBuilderJoin(rs);
						list.add(obj);
					}
				}
			}
		}catch(SQLException e) {
			LoggerSingleton.getExceptionLogger().warn("Failed to get reimbursments method 3:",e);
		}
		return list;
	}
	@Override
	public List<Reimbursment> list(User user, int limit, int offset) {
		List<Reimbursment> list = new ArrayList<>();
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = selectJoin
					+ joinTables
					+ "WHERE main.reimb_author = ? "
					+ "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setInt(1, user.getId());
				stmt.setInt(2, offset);
				stmt.setInt(3, limit);
				try(ResultSet rs = stmt.executeQuery()){
					while(rs.next()) {
						Reimbursment obj = objectBuilderJoin(rs);
						list.add(obj);
					}
				}
			}
		}catch(SQLException e) {
			LoggerSingleton.getExceptionLogger().warn("Failed to get reimbursments method 4:",e);
		}
		return list;
	}
	@Override
	public List<Reimbursment> list(int limit, int offset, ReimbursmentStatus status) {
		List<Reimbursment> list = new ArrayList<>();
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = selectJoin
					+ joinTables
					+ "WHERE main.reimb_status_id = ? "
					+ "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setInt(1, status.getId());
				stmt.setInt(2, offset);
				stmt.setInt(3, limit);
				try(ResultSet rs = stmt.executeQuery()){
					while(rs.next()) {
						Reimbursment obj = objectBuilderJoin(rs);
						list.add(obj);
					}
				}
			}
		}catch(SQLException e) {
			LoggerSingleton.getExceptionLogger().warn("Failed to get reimbursments method 5: ",e);
		}
		return list;
	}
	@Override
	public List<Reimbursment> list(User user, int limit, int offset, ReimbursmentStatus status) {
		List<Reimbursment> list = new ArrayList<>();
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = selectJoin
					+ joinTables
					+ "WHERE main.reimb_status_id = ? AND main.reimb_author = ? "
					+ "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setInt(1, status.getId());
				stmt.setInt(2, user.getId());
				stmt.setInt(3, offset);
				stmt.setInt(4, limit);
				try(ResultSet rs = stmt.executeQuery()){
					while(rs.next()) {
						Reimbursment obj = objectBuilderJoin(rs);
						list.add(obj);
					}
				}
			}
		}catch(SQLException e) {
			LoggerSingleton.getExceptionLogger().warn("Failed to get reimbursments method 6: ",e);
		}
		return list;
	}

	@Override
	public SerialBlob getBlob(int reimbursmentId) {
		SerialBlob result = null;
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "SELECT reimb_receipt FROM ADMIN.ERS_REIMBURSEMENT "
					+ "WHERE reimb_id = ?";
			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setInt(1, reimbursmentId);
				try(ResultSet rs = stmt.executeQuery()){
					while(rs.next()) {
						result = new SerialBlob(rs.getBlob("reimb_receipt"));
					}
				}catch(Exception e) {
					LoggerSingleton.getExceptionLogger().error("Reimbursment.getBlob: Failed to get result set: ", e);
				}
			}catch(Exception e){
				LoggerSingleton.getExceptionLogger().error("Reimbursment.getBlob: Failed to prepare statement: ", e);
			}
		}catch(SQLException e) {
			LoggerSingleton.getExceptionLogger().warn("Reimbursment.getBlob: Failed to get blob of receipt",e);
		}catch(Exception e) {
			LoggerSingleton.getExceptionLogger().error("Reimbursment.getBlob: Failed to establish connection: ", e);
		}
		return result;
	}
	
	@Override
	public Reimbursment get(int reimbursmentId) {
		Reimbursment result = null;
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = selectJoin
					+ joinTables
					+ "WHERE reimb_id = ?";
			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setInt(1, reimbursmentId);
				try(ResultSet rs = stmt.executeQuery()){
					while(rs.next()) {
						result = objectBuilderJoin(rs);
					}
				}catch(Exception e) {
					LoggerSingleton.getExceptionLogger().error("Reimbursment.get: Failed to get result set: ", e);
				}
			}catch(Exception e){
				LoggerSingleton.getExceptionLogger().error("Reimbursment.get: Failed to prepare statement: ", e);
			}
		}catch(SQLException e) {
			LoggerSingleton.getExceptionLogger().warn("Failed to get reimbursment",e);
		}catch(Exception e) {
			LoggerSingleton.getExceptionLogger().error("Reimbursment.get: Failed to establish connection: ", e);
		}
		return result;
	}
	
	public boolean update(SerialBlob blob, int reimbursmentId) {
		boolean result = false;
		if(blob == null)return result;
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "UPDATE ADMIN.ERS_REIMBURSEMENT SET "
				+ "reimb_receipt = ? "
				+ "WHERE reimb_id = ?";

			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setBlob(1, blob.getBinaryStream());
				stmt.setInt(2, reimbursmentId);
				int rs = stmt.executeUpdate();
				if (rs > 0) {
					result = true;
				}
			}
		}catch(SQLException e) {
			LoggerSingleton.getExceptionLogger().warn("Failed to update reimbursment ",e);
		}
		return result;
	}

	@Override
	public Reimbursment update(Reimbursment reimbursmentToUpdate) {
		Reimbursment result = null;
		try (Connection conn = DAOUtilities.getConnection()){
			String sql = "UPDATE ADMIN.ERS_REIMBURSEMENT SET "
				+ "reimb_resolved = ?, reimb_description = ?, "
				+ "reimb_resolver = ?, reimb_status_id = ?, "
				+ "reimb_type_id = ? "
				+ "WHERE reimb_id = ?";

			try(PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setDate(1, reimbursmentToUpdate.getResolved());
				stmt.setString(2, reimbursmentToUpdate.getDescription());
				stmt.setInt(3, reimbursmentToUpdate.getResolver().getId());
				stmt.setInt(4, reimbursmentToUpdate.getStatus().getId());
				stmt.setInt(5, reimbursmentToUpdate.getType().getId());
				stmt.setInt(6, reimbursmentToUpdate.getId());
				int rs = stmt.executeUpdate();
				if (rs > 0) {
					result = reimbursmentToUpdate;
				}
			}
		}catch(SQLException e) {
			LoggerSingleton.getExceptionLogger().warn("Failed to update reimbursment ",e);
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
			LoggerSingleton.getExceptionLogger().warn("Failed to delete reimbursment ",e);
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
			LoggerSingleton.getExceptionLogger().warn("Failed to get highest id",e);
		}
		return result;
	}
	private Reimbursment objectBuilderJoin(ResultSet rs) throws SQLException {
		/**
		 * Use this block of code to get the complete list of column names to reset the column numbers when the table is changed.
		 */
//		ResultSetMetaData rsmd = rs.getMetaData();
//		int length = rsmd.getColumnCount();
//		for (int a = 1; a<=length; a++) {
//			System.out.print(rsmd.getColumnName(a)+": ");
//			System.out.println(rs.getString(a));
//		}
//		System.out.println("");
		Reimbursment result =  new Reimbursment();
		result.setAmount(rs.getDouble("reimb_amount"));
		result.setSubmitted(rs.getDate("reimb_submitted"));
		result.setDescription(rs.getString("reimb_description"));
		result.setId(rs.getInt("reimb_id"));
		result.setResolved(rs.getDate("reimb_resolved"));
		User author = new User();
		if(rs.getInt("reimb_author")>0) {
			author.setId(rs.getInt(10));
			author.setUsername(rs.getString(11));
			author.setPassword(rs.getString(12));
			author.setFirstName(rs.getString(13));
			author.setLastName(rs.getString(14));
			author.setEmail(rs.getString(15));
			UserRole role = new UserRole();
			if(rs.getInt(16)>0) {
				role.setId(rs.getInt(17));
				role.setRole(rs.getString(18));
			}
			author.setRole(role);
		}
		result.setAuthor(author);
		User resolver = new User();
		if(rs.getInt("reimb_resolver")>0) {
			resolver.setId(rs.getInt(19));
			resolver.setUsername(rs.getString(20));
			resolver.setPassword(rs.getString(21));
			resolver.setFirstName(rs.getString(22));
			resolver.setLastName(rs.getString(23));
			resolver.setEmail(rs.getString(24));
			UserRole role = new UserRole();
			if(rs.getInt(25)>0) {
				role.setId(rs.getInt(26));
				role.setRole(rs.getString(27));
			}
			resolver.setRole(role);	
		}
		result.setResolver(resolver);
		ReimbursmentStatus status = new ReimbursmentStatus();
		if(rs.getInt("reimb_status_id")>0) {
			status.setId(rs.getInt(28));
			status.setStatus(rs.getString(29));
		}
		result.setStatus(status);
		ReimbursmentType type = new ReimbursmentType();
		if(rs.getInt("reimb_type_id")>0) {
			type.setId(rs.getInt(30));
			type.setType(rs.getString(31));
		}
		result.setType(type);
		return result;
	}
}
