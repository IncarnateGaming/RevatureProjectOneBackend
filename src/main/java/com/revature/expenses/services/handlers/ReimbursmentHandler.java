package com.revature.expenses.services.handlers;

import java.util.List;

import com.revature.expenses.dao.DAOUtilities;
import com.revature.expenses.dao.interfaces.ReimbursmentDAO;
import com.revature.expenses.models.Reimbursment;
import com.revature.expenses.models.User;

public class ReimbursmentHandler {
	private ReimbursmentDAO repository = null;
	public ReimbursmentHandler() {
		super();
		this.repository = DAOUtilities.getReimbursmentDao();
	}
	public ReimbursmentHandler(ReimbursmentDAO repository) {
		super();
		this.repository = repository;
	}
	public Reimbursment create(Reimbursment reimbursmentToCreate) {
		return repository.create(reimbursmentToCreate);
	}
	public List<Reimbursment> list() {
		return repository.list();
	}
	public List<Reimbursment> list(User user){
		return repository.list(user);
	}
	public List<Reimbursment> list(int limit, int offset){
		return repository.list(limit, offset);
	}
	public List<Reimbursment> list(User user, int limit, int offset){
		return repository.list(user, limit, offset);
	}
	public List<Reimbursment> list(int limit, int offset, int status){
		return repository.list(limit, offset, status);
	}
	public List<Reimbursment> list(User user, int limit, int offset, int status){
		return repository.list(user, limit, offset, status);
	}
	public Reimbursment get(int reimbursmentId) {
		if(reimbursmentId <= 0)return null;
		return repository.get(reimbursmentId);
	}
	public Reimbursment update(Reimbursment reimbursmentToUpdate) {
		return repository.update(reimbursmentToUpdate);
	}
	public boolean delete(Reimbursment reimbursmentToDelete) {
		return repository.delete(reimbursmentToDelete);
	}
	public int getHighestId() {
		return repository.getHighestId();
	}
}
