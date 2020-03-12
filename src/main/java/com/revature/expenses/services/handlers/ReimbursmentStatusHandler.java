package com.revature.expenses.services.handlers;

import java.util.List;

import com.revature.expenses.dao.DAOUtilities;
import com.revature.expenses.dao.interfaces.ReimbursmentStatusDAO;
import com.revature.expenses.models.ReimbursmentStatus;

public class ReimbursmentStatusHandler {
	private ReimbursmentStatusDAO repository = null;
	public ReimbursmentStatusHandler() {
		super();
		this.repository = DAOUtilities.getReimbursmentStatusDao();
	}
	public ReimbursmentStatusHandler(ReimbursmentStatusDAO repository) {
		super();
		this.repository = repository;
	}
	public ReimbursmentStatus create(ReimbursmentStatus reimbursmentStatusToCreate) {
		return repository.create(reimbursmentStatusToCreate);
	}
	public List<ReimbursmentStatus> list() {
		return repository.list();
	}
	public ReimbursmentStatus get(int reimbursmentStatusId) {
		if(reimbursmentStatusId <= 0)return null;
		return repository.get(reimbursmentStatusId);
	}
	public ReimbursmentStatus update(ReimbursmentStatus reimbursmentStatusToUpdate) {
		return repository.update(reimbursmentStatusToUpdate);
	}
	public boolean delete(ReimbursmentStatus reimbursmentStatusToDelete) {
		return repository.delete(reimbursmentStatusToDelete);
	}
	public int getHighestId() {
		return repository.getHighestId();
	}
}
