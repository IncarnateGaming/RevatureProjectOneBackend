package com.revature.expenses.dao.interfaces;

import java.util.List;

import com.revature.expenses.models.Reimbursment;

public interface ReimbursmentDAO {
	Reimbursment create(Reimbursment reimbursmentToCreate);
	List<Reimbursment> list();
	Reimbursment get(int reimbursmentId);
	Reimbursment update(Reimbursment reimbursmentToUpdate);
	boolean delete(Reimbursment reimbursmentToDelete);
	int getHighestId();
}
