package com.revature.expenses.services.handlers;

import java.util.List;

import com.revature.expenses.dao.DAOUtilities;
import com.revature.expenses.dao.interfaces.UserDAO;
import com.revature.expenses.models.User;

public class UserHandler {
	private UserDAO repository = null;
	public UserHandler() {
		super();
		this.repository = DAOUtilities.getUserDao();
	}
	public UserHandler(UserDAO repository) {
		super();
		this.repository = repository;
	}
	public User create(User userToCreate) {
		return repository.create(userToCreate);
	}
	public List<User> list() {
		return repository.list();
	}
	public User get(int userId) {
		if(userId <= 0)return null;
		return repository.get(userId);
	}
	public User update(User userToUpdate) {
		return repository.update(userToUpdate);
	}
	public boolean delete(User userToDelete) {
		return repository.delete(userToDelete);
	}
	public int getHighestId() {
		return repository.getHighestId();
	}
}
