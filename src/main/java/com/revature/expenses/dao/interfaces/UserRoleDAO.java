package com.revature.expenses.dao.interfaces;

import java.util.List;

import com.revature.expenses.models.UserRole;

public interface UserRoleDAO {
	UserRole create(UserRole userRoleToCreate);
	List<UserRole> list();
	UserRole get(int userRoleId);
	UserRole get(String userRole);
	UserRole update(UserRole userRoleToUpdate);
	boolean delete(UserRole userRoleToDelete);
	int getHighestId();
}
