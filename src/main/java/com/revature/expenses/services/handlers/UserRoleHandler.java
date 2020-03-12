package com.revature.expenses.services.handlers;

import java.util.List;

import com.revature.expenses.dao.DAOUtilities;
import com.revature.expenses.dao.interfaces.UserRoleDAO;
import com.revature.expenses.models.UserRole;

public class UserRoleHandler {
	private UserRoleDAO repository = null;
	public UserRoleHandler() {
		super();
		this.repository = DAOUtilities.getUserRoleDao();
	}
	public UserRoleHandler(UserRoleDAO repository) {
		super();
		this.repository = repository;
	}
	public UserRole create(UserRole userRoleToCreate) {
		return repository.create(userRoleToCreate);
	}
	public List<UserRole> list() {
		return repository.list();
	}
	public UserRole get(int userRoleId) {
		if(userRoleId <= 0)return null;
		return repository.get(userRoleId);
	}
	public UserRole update(UserRole userRoleToUpdate) {
		return repository.update(userRoleToUpdate);
	}
	public boolean delete(UserRole userRoleToDelete) {
		return repository.delete(userRoleToDelete);
	}
	public int getHighestId() {
		return repository.getHighestId();
	}
}
