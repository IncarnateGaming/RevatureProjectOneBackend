package com.revature.expenses.dao.interfaces;

import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import com.revature.expenses.models.Reimbursment;
import com.revature.expenses.models.ReimbursmentStatus;
import com.revature.expenses.models.User;

public interface ReimbursmentDAO {
	Reimbursment create(Reimbursment reimbursmentToCreate);
	List<Reimbursment> list();
	List<Reimbursment> list(User user);
	List<Reimbursment> list(int limit, int offset);
	List<Reimbursment> list(User user, int limit, int offset);
	List<Reimbursment> list(int limit, int offset, ReimbursmentStatus status);
	List<Reimbursment> list(User user, int limit, int offset, ReimbursmentStatus status);
	Reimbursment get(int reimbursmentId);
	SerialBlob getBlob(int reimbursmentId);
	boolean update(SerialBlob blob, int reimbursmentId);
	Reimbursment update(Reimbursment reimbursmentToUpdate);
	boolean delete(Reimbursment reimbursmentToDelete);
	int getHighestId();
}
