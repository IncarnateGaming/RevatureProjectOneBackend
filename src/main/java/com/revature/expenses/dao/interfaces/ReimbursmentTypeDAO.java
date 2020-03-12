package com.revature.expenses.dao.interfaces;

import java.util.List;

import com.revature.expenses.models.ReimbursmentType;

public interface ReimbursmentTypeDAO {
	ReimbursmentType create(ReimbursmentType reimbursmentTypeToCreate);
	List<ReimbursmentType> list();
	ReimbursmentType get(int reimbursmentTypeId);
	ReimbursmentType update(ReimbursmentType reimbursmentTypeToUpdate);
	boolean delete(ReimbursmentType reimbursmentTypeToDelete);
	int getHighestId();
}
