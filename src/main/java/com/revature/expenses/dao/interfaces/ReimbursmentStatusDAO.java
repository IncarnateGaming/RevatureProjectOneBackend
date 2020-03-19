package com.revature.expenses.dao.interfaces;

import java.util.List;

import com.revature.expenses.models.ReimbursmentStatus;

public interface ReimbursmentStatusDAO {
	ReimbursmentStatus create(ReimbursmentStatus reimbursmentStatusToCreate);
	List<ReimbursmentStatus> list();
	ReimbursmentStatus get(int reimbursmentStatusId);
	ReimbursmentStatus get(String status);
	ReimbursmentStatus update(ReimbursmentStatus reimbursmentStatusToUpdate);
	boolean delete(ReimbursmentStatus reimbursmentStatusToDelete);
	int getHighestId();
}
