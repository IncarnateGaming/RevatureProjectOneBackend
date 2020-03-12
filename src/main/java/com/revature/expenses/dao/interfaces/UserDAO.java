package com.revature.expenses.dao.interfaces;

import java.util.List;

import com.revature.expenses.models.User;

public interface UserDAO {
	User create(User reimbursmentToCreate);
	List<User> list();
	User get(int reimbursmentId);
	User update(User reimbursmentToUpdate);
	boolean delete(User reimbursmentToDelete);
	int getHighestId();
}
