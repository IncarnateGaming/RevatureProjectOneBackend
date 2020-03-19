package com.revature.expenses.services.handlers;

import java.util.List;

import com.revature.expenses.dao.DAOUtilities;
import com.revature.expenses.dao.interfaces.ReimbursmentStatusDAO;
import com.revature.expenses.models.ReimbursmentStatus;

public class ReimbursmentStatusHandler {
	private ReimbursmentStatusDAO repository = null;
	private static ReimbursmentStatus approved;
	private static ReimbursmentStatus rejected;
	private static ReimbursmentStatus pending;
	public ReimbursmentStatusHandler() {
		super();
		this.repository = DAOUtilities.getReimbursmentStatusDao();
	}
	public ReimbursmentStatusHandler(ReimbursmentStatusDAO repository) {
		super();
		this.repository = repository;
	}
	public ReimbursmentStatus getApproved() {
		if(approved == null) {
			approved = get("Approved");
		}
		return approved;
	}
	public ReimbursmentStatus getRejected() {
		if(rejected == null) {
			rejected = get("Rejected");
		}
		return rejected;
	}
	public ReimbursmentStatus getPending() {
		if(pending == null) {
			pending = get("Pending");
		}
		return pending;
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
	public ReimbursmentStatus get(String status) {
		return repository.get(status);
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
