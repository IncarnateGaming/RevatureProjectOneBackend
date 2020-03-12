package com.revature.expenses.services.handlers;

import java.util.List;

import com.revature.expenses.dao.DAOUtilities;
import com.revature.expenses.dao.interfaces.ReimbursmentTypeDAO;
import com.revature.expenses.models.ReimbursmentType;

public class ReimbursmentTypeHandler {
	private ReimbursmentTypeDAO repository = null;
	public ReimbursmentTypeHandler() {
		super();
		this.repository = DAOUtilities.getReimbursmentTypeDao();
	}
	public ReimbursmentTypeHandler(ReimbursmentTypeDAO repository) {
		super();
		this.repository = repository;
	}
	public ReimbursmentType create(ReimbursmentType reimbursmentTypeToCreate) {
		return repository.create(reimbursmentTypeToCreate);
	}
	public List<ReimbursmentType> list() {
		return repository.list();
	}
	public ReimbursmentType get(int reimbursmentTypeId) {
		if(reimbursmentTypeId <= 0)return null;
		return repository.get(reimbursmentTypeId);
	}
	public ReimbursmentType update(ReimbursmentType reimbursmentTypeToUpdate) {
		return repository.update(reimbursmentTypeToUpdate);
	}
	public boolean delete(ReimbursmentType reimbursmentTypeToDelete) {
		return repository.delete(reimbursmentTypeToDelete);
	}
	public int getHighestId() {
		return repository.getHighestId();
	}
}
